@Getter
public enum ResultCode {
    SUCCESS(200, "success"),
    PARAMETER_ERROR(400, "parameter error"),
    SYSTEM_ERROR(500, "system error"),
    UNAUTHORIZED(403, "unauthorized"),
    FREQUENT_REQUEST(429, "Too Frequent Request.");
}
