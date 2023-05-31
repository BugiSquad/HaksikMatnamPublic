package BugiSquad.HaksikMatnam.menu.mapper;

import BugiSquad.HaksikMatnam.menu.entity.NewMenu;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import BugiSquad.HaksikMatnam.menu.etc.NewMenuDto;
import BugiSquad.HaksikMatnam.menu.etc.NewMenuPostDto;

public class NewMenuMapper {

    public static NewMenuDto toDto(NewMenu newMenu) {

        return new NewMenuDto(newMenu.getId(),
                newMenu.getName(),
                newMenu.getDetail(),
                newMenu.getImageUrl(),
                newMenu.getVotes(),
                newMenu.getCategory().name(),
                newMenu.getModifiedAt());
    }

    public static NewMenu toEntityByPost(NewMenuPostDto newMenuPostDto) {

        return new NewMenu(newMenuPostDto.getName(),
                newMenuPostDto.getDetail(),
                newMenuPostDto.getUrl(),
                0,
                MenuCategory.valueOf(newMenuPostDto.getCategory()));
    }
}
