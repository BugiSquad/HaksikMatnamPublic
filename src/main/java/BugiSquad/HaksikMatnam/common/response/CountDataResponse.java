package BugiSquad.HaksikMatnam.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountDataResponse<T> {

    private int status;
    private T data;
    private int itemCount;

    public CountDataResponse (final int status, T data, final int itemCount) {
        this.status = status;
        this.data = data;
        this.itemCount = itemCount;
    }

    public static<T> CountDataResponse<T> response(final int status, T data, final int itemCount) {
        return CountDataResponse.<T>builder()
                .status(status)
                .data(data)
                .itemCount(itemCount)
                .build();
    }
}
