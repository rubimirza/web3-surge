
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

import static com.cyber.kepler.farm.constant.Constants.API_AUTH_FILTER;
import static com.cyber.kepler.farm.enums.ResultCode.UNAUTHORIZED;


@RequiredArgsConstructor
@Slf4j
@Order(API_AUTH_FILTER)
@Aspect
@Component
public class ApiAuthAspect {

    private final FarmProperties nftProperties;

    @Pointcut("@annotation(com.cyber.kepler.farm.annotation.AuthApi)")
    public void authApi() {
    }

    @Around("authApi()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        Signature sig = pjp.getSignature();
//        if (!(sig instanceof MethodSignature methodSignature)) {
//            throw new IllegalArgumentException("This annotation acts only on Methods !");
//        }
//        Method method = methodSignature.getMethod();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        var headerKey = request.getHeader("apiKey");
//        var properKey = this.nftProperties.getInternal().getApiKey();
//        if (!StringUtils.equals(headerKey, properKey)) {
//            UNAUTHORIZED.throwException();
//        }
        return pjp.proceed();
    }
}
