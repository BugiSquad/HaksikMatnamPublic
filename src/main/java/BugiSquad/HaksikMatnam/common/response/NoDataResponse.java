package BugiSquad.HaksikMatnam.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoDataResponse {
    private int status;
    private NoDataFormat data = new NoDataFormat();

    public NoDataResponse(final int status) {
        this.status = status;
    }
}
