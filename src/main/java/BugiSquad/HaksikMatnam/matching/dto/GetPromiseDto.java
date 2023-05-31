package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.Promise;
import BugiSquad.HaksikMatnam.matching.entity.PromiseMemberRelation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPromiseDto {
    private Long promiseId;
    private String location;
    private LocalDateTime promisedTime;
    private List<MemberMeetingDto> memberMeetingDto;

    public GetPromiseDto(PromiseMemberRelation promiseMemberRelation) {
        Promise promise = promiseMemberRelation.getPromise();
        this.promiseId = promise.getId();
        this.location = promise.getLocation();
        this.promisedTime = promise.getPromisedTime();
    }
}
