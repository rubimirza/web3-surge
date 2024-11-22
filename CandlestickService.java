

import com.alibaba.fastjson.JSON;
import com.socrates.wallet.config.KlineProperties;
import com.socrates.wallet.entity.TokenCandle;
import com.socrates.wallet.util.DateUtil;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;


@RequiredArgsConstructor
@Slf4j
@Component
public class CandlestickService {

    @Resource(name = "marketHistoryKlineHeader")
    private final Map<String, TokenCandle> marketHistoryKlineHeader;

    private final KlineProperties klineProperties;


    /**
     * 消息处理入口, 这里只是修改头结点的内存数据
     */
    public void process(Map<String, String[]> records) {
        records.entrySet().stream().map(process()).forEach(footprint());
    }

    private Consumer<Stream<Option<TokenCandle>>> footprint() {
        return list -> list.filter(Option::isDefined).map(Option::get)
                .forEach(c -> {
                    if (log.isDebugEnabled()) {
                        log.debug("Update Kline data for Amount and Volume: {}", JSON.toJSONString(c));
                    }
                });
    }

    private Function<Map.Entry<String, String[]>, Stream<Option<TokenCandle>>> process() {
        var timeScales = klineProperties.getTimeScales();
        return entry -> {
            var symbol = entry.getKey();
            var time = Long.parseLong(entry.getValue()[0]);
            var amount = new BigDecimal(entry.getValue()[1]);
            var volume = new BigDecimal(entry.getValue()[2]);
            return Stream.ofAll(timeScales.entrySet()).flatMap(timeScale -> {
                var dateType = timeScale.getKey();
                var duration = timeScale.getValue();
                var currentMinuteTime = DateUtil.getCurrentTime(time, duration);
                var result = pipe(marketHistoryKlineHeader, symbol, dateType, currentMinuteTime, amount, volume);
                return List.of(result);
            });
        };
    }

    private Option<TokenCandle> pipe(Map<String, TokenCandle> map, String symbol, String dateType, long currentTime, BigDecimal amount, BigDecimal volume) {
        var key = String.format("%s_%s", symbol, dateType);
        var klineDay = map.get(key);
        return Option.of(klineDay).flatMap(c -> update(currentTime, amount, volume).apply(c));
    }

    private Function<TokenCandle, Option<TokenCandle>> update(long currentTime, BigDecimal amount, BigDecimal volume) {
        return current -> {
            if (log.isDebugEnabled()) {
                log.debug("candlestick symbol: {}, type: {}, dataType: {}, time: {},  dataTime: {}, diff: {}", current.getSymbol(), current.getDateType(), current.getDateType(), current.getTime(), currentTime, (currentTime - current.getTime()));
            }
            // 只要数据比头节点新，则就一直更新累加值
            if (currentTime - current.getTime() >= 0) {
                // 如果还没到切节点，则一直累计这个值
                update(current, amount, volume);
                return Option.of(current);
            }
            return Option.none();
        };
    }

    private void update(TokenCandle tokenCandle, BigDecimal amount, BigDecimal volume) {
        tokenCandle.setAmount(Option.of(tokenCandle.getAmount()).getOrElse(BigDecimal.ZERO).add(amount));
        tokenCandle.setVolume(Option.of(tokenCandle.getVolume()).getOrElse(BigDecimal.ZERO).add(volume));
    }


}
