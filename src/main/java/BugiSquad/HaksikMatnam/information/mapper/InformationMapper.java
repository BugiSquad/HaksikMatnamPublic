package BugiSquad.HaksikMatnam.information.mapper;

import BugiSquad.HaksikMatnam.information.entity.Information;
import BugiSquad.HaksikMatnam.information.etc.InformationDto;
import BugiSquad.HaksikMatnam.information.etc.InformationPostDto;

public class InformationMapper {

    public static InformationDto toDto(Information information) {

        return InformationDto.builder()
                .informationId(information.getId())
                .title(information.getTitle())
                .text(information.getText())
                .modifiedAt(information.getModifiedAt())
                .build();
    }

    public static Information toEntityByPost(InformationPostDto postDto) {

        return Information.builder()
                .title(postDto.getTitle())
                .text(postDto.getText())
                .build();
    }
}
