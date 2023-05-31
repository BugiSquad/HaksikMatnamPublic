package BugiSquad.HaksikMatnam.matching.service;

import BugiSquad.HaksikMatnam.matching.repository.memberNoteRoomRelation.MemberNoteRoomRelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final MemberNoteRoomRelationRepository memberNoteRoomRelationRepository;


}
