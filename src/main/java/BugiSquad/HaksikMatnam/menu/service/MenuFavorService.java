package BugiSquad.HaksikMatnam.menu.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.member.mapper.ReviewMapper;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.entity.MenuFavor;
import BugiSquad.HaksikMatnam.menu.etc.MenuFavorDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuFavorSortDto;
import BugiSquad.HaksikMatnam.menu.mapper.MenuMapper;
import BugiSquad.HaksikMatnam.menu.repository.MenuFavorRepository;
import BugiSquad.HaksikMatnam.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuFavorService {

    private final MenuFavorRepository menuFavorRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<CountDataResponse<List<MenuFavorSortDto>>> findFavorListBySorting(int year, int month) {

        // 저번달 목록을 가져오므로 운영을 시작한 달은 아무것도 안뜨는 것이 정상. 시연시 보여주고 싶으면 month에 +1
        List<MenuFavor> menuFavorList = menuFavorRepository.findAllByCountsYearAndCountsMonth(year, month);
        List<Menu> menuList = menuRepository.findAll();
        if (menuFavorList.isEmpty() || menuList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<MenuFavorSortDto> menuFavorDtoList = new ArrayList<>();

        for (Menu menu : menuList) {
            MenuFavor menuFavor = menuFavorList.stream().filter(favor -> favor.getMenu().equals(menu)).findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
            menuFavorDtoList.add(
                    MenuFavorSortDto.builder()
                            .menuId(menu.getId())
                            .name(menu.getName())
                            .detail(menu.getDetail())
                            .imageUrl(menu.getImageUrl())
                            .price(menu.getPrice())
                            .totalRating(menu.getTotalRating())
                            .category(menu.getCategory().name())
                            .reviewList(menu.getReviewList().stream().map(ReviewMapper::toCompactDto).toList())
                            .modifiedAt(menu.getModifiedAt())
                            .orderCount(menuFavor.getCountsNum())
                            .build()
            );
        }

        return ResponseEntity.ok(CountDataResponse.response(200, menuFavorDtoList.stream().sorted(Comparator.comparingInt(MenuFavorSortDto::getOrderCount).reversed()).toList(), menuFavorDtoList.size()));
    }

    public ResponseEntity<CountDataResponse<List<MenuFavorDto>>> findMonthList(int year, int month, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        List<MenuFavor> countList = menuFavorRepository.findAllByCountsYearAndCountsMonth(year, month);

        return countList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, countList.stream().map(menuFavor ->
                        MenuFavorDto.builder()
                                .menuCompactDto(MenuMapper.toCompactDto(menuFavor.getMenu()))
                                .count(menuFavor.getCountsNum())
                                .year(menuFavor.getCountsYear())
                                .month(menuFavor.getCountsMonth())
                                .build())
                        .toList(), countList.size()));
    }

    @Transactional
    public void updateThisMonthList() {

        LocalDateTime now = LocalDateTime.now();
        List<Menu> all = menuRepository.findAll();
        for (Menu menu : all) {
            MenuFavor menuFavor = new MenuFavor(menu, 0, now.getYear(), now.getMonthValue());
            menuFavor.initCreatedAt(now);
            menuFavorRepository.save(menuFavor);
            menu.getCountList().add(menuFavor);
        }
    }
}
