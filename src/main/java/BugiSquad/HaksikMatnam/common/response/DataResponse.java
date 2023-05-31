package BugiSquad.HaksikMatnam.common.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResponse<T> {
    private int status;
    private T data;

    public DataResponse(final int status, T data) {
        this.status = status;
        this.data = data;
    }

    public static<T> DataResponse<T> response(final int status, T data) {
        return DataResponse.<T>builder()
                .status(status)
                .data(data)
                .build();
    }
}

