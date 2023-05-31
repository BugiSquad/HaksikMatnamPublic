package BugiSquad.HaksikMatnam.member.mapper;

import BugiSquad.HaksikMatnam.member.entity.Interest;
import BugiSquad.HaksikMatnam.member.etc.InterestDto;
import BugiSquad.HaksikMatnam.member.etc.InterestPostDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InterestMapper {

    public static InterestDto toDto(Interest interest) {

        return new InterestDto(interest.getId(), interest.getPubg(), interest.getLol(), interest.getCelebrity(),
                interest.getCoffee(), interest.getDessert(), interest.getGame(), interest.getPopSong(), interest.getKPop(),
                interest.getJPop(), interest.getDrama(), interest.getMovie(), interest.getTravel(), interest.getStudy(),
                interest.getHiking(), interest.getBook(), interest.getModifiedAt());
    }

    public static Interest toEntityByPost(InterestPostDto dto) {

        return new Interest(dto.isPubg(), dto.isLol(), dto.isCelebrity(), dto.isCoffee(), dto.isDessert(),
                dto.isGame(), dto.isPopSong(), dto.isKPop(), dto.isJPop(), dto.isDrama(), dto.isMovie(),
                dto.isTravel(), dto.isStudy(), dto.isHiking(), dto.isBook());
    }

    public static List<String> onlyTrueAtStringList(Interest interest) throws IllegalAccessException {
        InterestDto dto = toDto(interest);

        List<String> interests = new ArrayList<>();

        // InterestDto 클래스의 모든 필드를 반복문으로 처리
        for (Field field : dto.getClass().getDeclaredFields()) {
            // 필드가 boolean 타입인 경우에만 처리
            if (field.getType() == boolean.class) {
                // 필드명과 값 추출
                String fieldName = field.getName();
                boolean fieldValue = field.getBoolean(dto);
                // 값이 true인 경우 변수명을 interests 리스트에 추가
                if (fieldValue) {
                    interests.add(fieldName);
                }
            }
        }
        return interests;
    }
}
