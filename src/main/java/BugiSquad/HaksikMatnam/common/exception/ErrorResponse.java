package BugiSquad.HaksikMatnam.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private final String status;
    private final ErrorData data;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        ErrorData data = new ErrorData(errorCode.getHttpStatus().name(), errorCode.name(), errorCode.getDetail());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status("400")
                        .data(data)
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode,
                                                                 String detail) {
        ErrorData data = new ErrorData(errorCode.getHttpStatus().name(), errorCode.name(), errorCode.getDetail());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status("400")
                        .data(data)
                        .build()
                );
    }
}
