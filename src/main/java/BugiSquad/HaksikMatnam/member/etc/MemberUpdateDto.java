package BugiSquad.HaksikMatnam.member.etc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberUpdateDto {

    private String phone;
    private String profileUrl;
    @Valid
    @Min(value = 1, message = "학년은 최소 1학년입니다.")
    private Integer grade;
    private String department;
}
