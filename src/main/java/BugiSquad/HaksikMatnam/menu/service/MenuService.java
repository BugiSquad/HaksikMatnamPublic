package BugiSquad.HaksikMatnam.menu.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.entity.Review;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.util.s3.S3Service;
import BugiSquad.HaksikMatnam.menu.entity.MenuFavor;
import org.springframework.web.multipart.MultipartFile;

import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuPostDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuUpdateDto;
import BugiSquad.HaksikMatnam.menu.mapper.MenuMapper;
import BugiSquad.HaksikMatnam.menu.repository.MenuFavorRepository;
import BugiSquad.HaksikMatnam.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuFavorRepository menuFavorRepository;
    private final MemberRepository memberRepository;
    private ThreadLocal<Double> threadLocalRatingSum = new ThreadLocal<>();
    private final S3Service s3service;


    private void setSumInThreadLocal(double sum) {
        threadLocalRatingSum.set(sum);
    }

    private double getSumByThreadLocal() {
        return threadLocalRatingSum.get();
    }

    private void removeSumInThreadLocal() {
        threadLocalRatingSum.remove();
    }

    public ResponseEntity<DataResponse<MenuDto>> findMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));

        return ResponseEntity.ok(DataResponse.response(200, MenuMapper.toDto(menu)));
    }

    public ResponseEntity<CountDataResponse<List<MenuDto>>> findMenusByCategory(MenuCategory category) {

        List<Menu> menuList = menuRepository.findAllByCategory(category);

        return menuList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, menuList.stream().map(MenuMapper::toDto).toList(), menuList.size()));
    }

    @Transactional
    public void updateTotalRating(Menu menu) {

        try {
            List<Review> reviewList = menu.getReviewList();
            setSumInThreadLocal(reviewList.stream().mapToDouble(Review::getRating).sum());
            menu.changeTotalRating(getSumByThreadLocal() / reviewList.size());
            menu.changeModifiedAt(LocalDateTime.now());
        } finally {
            removeSumInThreadLocal();
        }
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postMenu(MenuPostDto menuPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        Menu menu = MenuMapper.toEntityByPost(menuPostDto);
        LocalDateTime now = LocalDateTime.now();
        menu.initCreatedAt(now);
        menuRepository.save(menu);
        MenuFavor menuFavor = new MenuFavor(menu, 0, now.getYear(), now.getMonthValue());
        menuFavor.initCreatedAt(now);
        menuFavorRepository.save(menuFavor);
        menu.getCountList().add(menuFavor);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> updateMenu(MenuUpdateDto menuUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        Menu menu = menuRepository.findById(menuUpdateDto.getMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        if (menuUpdateDto.getName() != null) {
            menu.changeName(menuUpdateDto.getName());
        }
        if (menuUpdateDto.getDetail() != null) {
            menu.changeDetail(menuUpdateDto.getDetail());
        }
        if (menuUpdateDto.getImageUrl() != null) {
            menu.changeImageUrl(menuUpdateDto.getImageUrl());
        }
        if (menuUpdateDto.getPrice() != null) {
            menu.changePrice(menuUpdateDto.getPrice());
        }
        if (menuUpdateDto.getCategory() != null) {
            menu.changeMenuCategory(MenuCategory.valueOf(menuUpdateDto.getCategory()));
        }
        menu.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteMenu(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        menuRepository.delete(menu);

        return ResponseEntity.ok(new NoDataResponse(200));
    }


    @Transactional
    public void updateMenuMvc(MenuDto menuDto) {

        Optional<Menu> findOp = menuRepository.findById(menuDto.getMenuId());

        Menu menu = findOp.get();
        if (menuDto.getName() != null) {
            menu.changeName(menuDto.getName());
        }
        if (menuDto.getDetail() != null) {
            menu.changeDetail(menuDto.getDetail());
        }
        if (menuDto.getImageUrl() != null) {
            menu.changeImageUrl(menuDto.getImageUrl());
        }
        if (menuDto.getPrice() != 0) {
            menu.changePrice(menuDto.getPrice());
        }
        if (menuDto.getCategory() != null) {
            menu.changeMenuCategory(MenuCategory.valueOf(menuDto.getCategory()));
        }
    }


    public List<MenuDto> findMenusAllMvc() {

        List<Menu> findOp = menuRepository.findAll();

        List<MenuDto> findMenus = findOp.stream().map(MenuMapper::toDto).collect(Collectors.toList());

        return findMenus;
    }


    public MenuDto findMenuById(Long id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        MenuDto dto = MenuMapper.toDto(menu);
        return dto;
    }

}
