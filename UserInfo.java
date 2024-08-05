

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserInfo {

    @JsonProperty(value = "user_id")
    private String userId;
    private String username;
    private String nickname;
    private String avatar;
    private String language;
    // 接口文档描述为：关注状态， edge无需关注
    private String status;
    // 用户金v认证状态：verified_state：VERIFIED:已认证 UNVERIFIED:未认证
    @JsonProperty(value = "verified_state")
    private String verifiedState;
}
