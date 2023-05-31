package BugiSquad.HaksikMatnam.menu.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.menu.entity.NewMenu;
import BugiSquad.HaksikMatnam.menu.etc.*;
import BugiSquad.HaksikMatnam.menu.mapper.NewMenuMapper;
import BugiSquad.HaksikMatnam.menu.repository.NewMenuRepository;
import BugiSquad.HaksikMatnam.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewMenuService {

    private final NewMenuRepository newMenuRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3service;


    public ResponseEntity<DataResponse<NewMenuDto>> findNewMenu(Long id) {

        NewMenu newMenu = newMenuRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));

        return ResponseEntity.ok(DataResponse.response(200, NewMenuMapper.toDto(newMenu)));
    }

    public ResponseEntity<CountDataResponse<List<NewMenuDto>>> findNewMenuListByCategory(MenuCategory category) {

        List<NewMenu> findList = newMenuRepository.findAllByCategory(category);

        return findList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, findList.stream().map(NewMenuMapper::toDto).toList(), findList.size()));
    }

    public ResponseEntity<CountDataResponse<List<NewMenuDto>>> findNewMenuList() {

        List<NewMenu> findList = newMenuRepository.findAll();

        return findList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, findList.stream().map(NewMenuMapper::toDto).toList(), findList.size()));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> voteToNewMenu(Long menuId, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        NewMenu newMenu = newMenuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        /**
         * 투표는 일주일 마다 가능 -> 투표 모집 측정 단위를 변경하려면 코드 수정 필요
         * member의 최근 투표 날짜를 확인하여 이번 주인 경우 투표 불가능
         */
        if (member.getVoteTime() != null && member.getVoteTime().get(weekFields.weekOfYear()) == LocalDate.now().get(weekFields.weekOfYear())) {
            throw new CustomException(ErrorCode.ALREADY_VOTES);
        }
        LocalDateTime now = LocalDateTime.now();
        member.changeVoteTime(now);
        member.changeModifiedAt(now);
        member.changeVoteMenuId(menuId);
        newMenu.increaseVotes();

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public ResponseEntity<DataResponse<CheckVoteDto>> checkVoteTime(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        boolean availableToVote = true;
        if (member.getVoteTime() != null && member.getVoteTime().get(weekFields.weekOfYear()) == LocalDate.now().get(weekFields.weekOfYear())) {
            availableToVote = false;
        }

        return ResponseEntity.ok(DataResponse.response(200, CheckVoteDto.builder()
                .voteTime(member.getVoteTime())
                .menuId(member.getVoteMenuId())
                .availableToVote(availableToVote)
                .build()));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postNewMenu(NewMenuPostDto newMenuPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        NewMenu newMenu = NewMenuMapper.toEntityByPost(newMenuPostDto);
        newMenu.initCreatedAt(LocalDateTime.now());
        newMenuRepository.save(newMenu);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> updateNewMenu(NewMenuUpdateDto newMenuUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        NewMenu newMenu = newMenuRepository.findById(newMenuUpdateDto.getNewMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        if (newMenuUpdateDto.getName() != null) {
            newMenu.changeName(newMenuUpdateDto.getName());
        }
        if (newMenuUpdateDto.getDetail() != null) {
            newMenu.changeDetail(newMenuUpdateDto.getDetail());
        }
        if (newMenuUpdateDto.getUrl() != null) {
            newMenu.changeImageUrl(newMenuUpdateDto.getUrl());
        }
        if (newMenuUpdateDto.getCategory() != null) {
            newMenu.changeMenuCategory(MenuCategory.valueOf(newMenuUpdateDto.getCategory()));
        }
        newMenu.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteNewMenu(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        NewMenu newMenu = newMenuRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        newMenuRepository.delete(newMenu);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public NewMenuDto findNewMenuMvc(Long id) {
        NewMenu newMenu = newMenuRepository.findById(id).get();
        return NewMenuMapper.toDto(newMenu);
    }

    public List<NewMenuDto> findNewMenusMvc() {

        List<NewMenu> findEntities = newMenuRepository.findAll();

        List<NewMenuDto> findNewMenus = findEntities.stream().map(NewMenuMapper::toDto).collect(Collectors.toList());

        return findNewMenus;
    }

    @Transactional
    public void deleteByIdMvc(Long id) {
        newMenuRepository.deleteById(id);
    }
}
