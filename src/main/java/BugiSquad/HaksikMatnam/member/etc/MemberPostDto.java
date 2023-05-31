package BugiSquad.HaksikMatnam.member.etc;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class MemberPostDto{

    @NotNull
    @Valid
    private String name;
    @NotNull
    @Valid
    private String phone;
    @Valid
    @Positive
    private int studentId;
    @NotNull
    @Valid
    @Email
    private String email;
    @NotNull
    @Valid
    private String profileUrl;
    @Valid
    @Min(value = 1, message = "학년은 최소 1학년입니다.")
    private int grade;
    @NotNull
    @Valid
    private String gender;
    @NotNull
    @Valid
    private String department;
    @NotNull
    @Valid
    private String password;
    @NotNull
    @Valid
    private InterestPostDto interestPostDto;
}
