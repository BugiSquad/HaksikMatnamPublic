package BugiSquad.HaksikMatnam.information.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.information.entity.Information;
import BugiSquad.HaksikMatnam.information.etc.InformationDto;
import BugiSquad.HaksikMatnam.information.etc.InformationPostDto;
import BugiSquad.HaksikMatnam.information.etc.InformationUpdateDto;
import BugiSquad.HaksikMatnam.information.mapper.InformationMapper;
import BugiSquad.HaksikMatnam.information.repository.InformationRepository;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<DataResponse<InformationDto>> findInformation(Long id) {

        Information information = informationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        return ResponseEntity.ok(DataResponse.response(200, InformationMapper.toDto(information)));
    }

    public ResponseEntity<DataResponse<Page<InformationDto>>> findInformationList(Pageable pageable) {

        Page<Information> findInfoList = informationRepository.findAll(pageable);

        return findInfoList.getContent().isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(DataResponse.response(200, findInfoList.map(InformationMapper::toDto)));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postInformation(InformationPostDto informationPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }

        Information information = InformationMapper.toEntityByPost(informationPostDto);
        information.initCreatedAt(LocalDateTime.now());
        informationRepository.save(information);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> updateInformation(InformationUpdateDto informationUpdateDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }

        Information information = informationRepository.findById(informationUpdateDto.getInformationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        if (informationUpdateDto.getText() != null) {
            information.updateText(informationUpdateDto.getText());
        }
        if (informationUpdateDto.getTitle() != null) {
            information.updateTitle(informationUpdateDto.getTitle());
        }
        information.changeModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteInformation(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }

        Information information = informationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        informationRepository.delete(information);

        return ResponseEntity.ok(new NoDataResponse(200));
    }
}
