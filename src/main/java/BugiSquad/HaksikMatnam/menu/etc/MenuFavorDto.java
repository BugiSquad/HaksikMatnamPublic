package BugiSquad.HaksikMatnam.menu.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuFavorDto {

    private MenuCompactDto menuCompactDto;
    private int count;
    private int year;
    private int month;

    @Builder
    public MenuFavorDto(MenuCompactDto menuCompactDto, int count, int year, int month) {
        this.menuCompactDto = menuCompactDto;
        this.count = count;
        this.year = year;
        this.month = month;
    }
}
