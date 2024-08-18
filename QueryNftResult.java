

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.socrates.wallet.dto.NftMetadata;
import com.socrates.wallet.serializer.BigDecimalPlainStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QueryNftResult {
    private Long assetId;
    private Long nftId;
    private String nftName;
    private String nftUrl;
    private NftMetadataWallet nftMetadata;
    private String nftChain;
    private String nftChainLabel;
    private String nftContractAddress;
    private Long nftType;
    private String nftProtocol;
    private String nftChainIcon;
    private BigDecimal amount;
    private String integrationAssetId;
}
