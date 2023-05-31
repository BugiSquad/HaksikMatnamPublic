package BugiSquad.HaksikMatnam.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //처리되지 않은 예외
    UNKNOWN_SERVER_ERROR(INTERNAL_SERVER_ERROR,"처리되지 않은 예외입니다. 서버관리자에게 문의하세요."),

    //잘못된 요청
    DUPLICATE_ENTITY_ERROR(BAD_REQUEST,"중복되어 저장할 수 없습니다."),
    INVALID_FIELD(BAD_REQUEST, "인자 형식이 맞지 않습니다."),
    WRONG_OBJECT(BAD_REQUEST, "객체 변환이 되지 않습니다. 옳은 형식을 보내주세요."),
    NOT_EXIST_MEMBER_NOTE_ROOM(BAD_REQUEST,"존재하는 유저나 쪽지방이 없거나, 유저가 쪽지방에 참여하지 않았습니다."),

    //member
    NOT_EXIST_MEMBER(BAD_REQUEST,"존재하는 유저가 없습니다."),
    NOT_ADMIN(UNAUTHORIZED, "관리자만 접근 가능합니다."),
    INVALID_LOGIN(BAD_REQUEST, "아이디 또는 비밀번호가 맞지 않습니다."),
    INVALID_USER_CART(BAD_REQUEST, "자신의 장바구니만 수정할 수 있습니다."),
    NOT_EXIST_CART(NOT_FOUND, "장바구니가 존재하지 않습니다."),

    //noteRoom
    NOT_EXIST_NOTE_ROOM(BAD_REQUEST,"존재하는 쪽지방이 없습니다."),

    // menu
    NOT_EXIST_MENU(NOT_FOUND, "존재하는 메뉴가 없습니다"),
    NOT_RESET_FAVOR(BAD_REQUEST, "이번달 메뉴 기록이 시작되지 않았습니다."),
    ALREADY_VOTES(BAD_REQUEST, "이미 이번주에는 투표하셨습니다."),

    // review
    NOT_EXIST_REVIEW(NOT_FOUND, "존재하는 리뷰가 없습니다."),
    INVALID_USER_REVIEW(BAD_REQUEST, "자신의 리뷰만 수정할 수 있습니다."),

    // message
    NOT_EXIST_SUBSCRIPTION(NOT_FOUND, "구독이 존재하지 않습니다."),
    INVALID_USER_SUBSCRIPTION(BAD_REQUEST, "자신의 구독만 삭제할 수 있습니다."),

    // order
    NOT_EXIST_ORDERS(NOT_FOUND, "주문을 찾을 수 없습니다."),
    INVALID_USER_ORDERS(BAD_REQUEST, "자신의 주문만 접근할 수 있습니다."),

    //post
    NOT_EXIST_POST(BAD_REQUEST,"존재하는 게시글이 없습니다."),

    //matching
    DO_NOT_APPLICATION_MY_MATCHING(BAD_REQUEST,"나의 매칭에는 신청할 수 없습니다."),
    IS_NOT_FIRST_APPLICATION(BAD_REQUEST,"중복된 신청입니다."),

    //notify
    IS_DELETED_NOTE_ROOM(BAD_REQUEST,"삭제된 채팅방입니다."),

    IOException(BAD_REQUEST,"IOException")
    ;
    private final HttpStatus httpStatus;
    private final String detail;
}
