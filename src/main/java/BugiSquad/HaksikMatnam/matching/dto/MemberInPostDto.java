package BugiSquad.HaksikMatnam.matching.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberInPostDto {

    private Long id;
    private String name;
    private String memberProfileUrl;
    private Integer yearOfEnter;
    private List<String> interest;


    public MemberInPostDto(Long id, String name, String memberProfileUrl, Integer yearOfEnter, List<String> interest) {
        this.id = id;
        this.name = name;
        this.memberProfileUrl = memberProfileUrl;
        this.yearOfEnter = yearOfEnter;
        this.interest = interest;
    }
}
