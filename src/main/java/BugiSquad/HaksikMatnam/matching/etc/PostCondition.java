package BugiSquad.HaksikMatnam.matching.etc;

import BugiSquad.HaksikMatnam.member.etc.Gender;
import lombok.Data;

import java.util.List;

@Data
public class PostCondition {
    private List<String> interest; //관심사
    private List<String> department; //전공
    private List<Integer> grade; //학년
    private Integer priorityTime; //우선시간, ex 30 -> 30분 뒤 까지 조회
    private List<Gender> gender; // 성별
    private GroupType groupType; //
}
