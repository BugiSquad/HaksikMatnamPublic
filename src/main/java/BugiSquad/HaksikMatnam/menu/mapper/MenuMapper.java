package BugiSquad.HaksikMatnam.menu.mapper;

import BugiSquad.HaksikMatnam.member.mapper.ReviewMapper;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import BugiSquad.HaksikMatnam.menu.etc.MenuCompactDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuPostDto;

import java.util.stream.Collectors;

public class MenuMapper {

    public static MenuDto toDto(Menu menu) {

        return new MenuDto(menu.getId(),
                menu.getName(),
                menu.getDetail(),
                menu.getImageUrl(),
                menu.getPrice(),
                menu.getTotalRating(),
                menu.getCategory().name(),
                menu.getReviewList().stream().map(ReviewMapper::toCompactDtoWithMember).toList(),
                menu.getModifiedAt());
    }

    public static MenuCompactDto toCompactDto(Menu menu) {

        return new MenuCompactDto(
                menu.getId(),
                menu.getName(),
                menu.getDetail(),
                menu.getImageUrl(),
                menu.getPrice(),
                menu.getTotalRating(),
                menu.getCategory().name()
        );
    }

    public static Menu toEntityByPost(MenuPostDto menuPostDto) {

        return new Menu(menuPostDto.getName(),
                menuPostDto.getDetail(),
                menuPostDto.getImageUrl(),
                menuPostDto.getPrice(),
                0,
                MenuCategory.valueOf(menuPostDto.getCategory()));
    }
}
