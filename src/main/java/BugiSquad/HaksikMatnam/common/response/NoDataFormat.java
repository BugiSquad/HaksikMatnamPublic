package BugiSquad.HaksikMatnam.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoDataFormat {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String error = "NONE";
    private String code = "SUCCESS";
    private String message = "성공했습니다.";
}