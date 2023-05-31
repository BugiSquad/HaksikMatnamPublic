package BugiSquad.HaksikMatnam.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorData {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String error;
    private final String code;
    private final String message;

    public ErrorData(String error, String code, String message) {
        this.error = error;
        this.code = code;
        this.message = message;
    }
}
