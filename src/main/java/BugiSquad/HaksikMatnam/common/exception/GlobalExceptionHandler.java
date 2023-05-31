package BugiSquad.HaksikMatnam.common.exception;



import BugiSquad.HaksikMatnam.common.response.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import static BugiSquad.HaksikMatnam.common.exception.ErrorCode.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {
        ObjectError objectError = Objects.requireNonNull(
                exception.getBindingResult().getAllErrors().stream().findFirst()
                        .orElse(null));
        log.error("handleValidationException throw MethodArgumentNotValidException : {} {}",
                INVALID_FIELD, exception.getStackTrace());
        return ErrorResponse.toResponseEntity(INVALID_FIELD, objectError.getDefaultMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadableException() {
        log.error("handleNotReadableException throw Exception : {}", WRONG_OBJECT);
        return ErrorResponse.toResponseEntity(WRONG_OBJECT);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<DataResponse<ErrorData>> MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException : {}", e.getMessage());
        return new ResponseEntity<>(DataResponse.response(400,new ErrorData("MissingServletRequestParameterException","ERROR",e.getMessage())),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<DataResponse<ErrorData>> NullPointerException(NullPointerException e) {
        log.error("MissingServletRequestParameterException : {}", e.getMessage());
        return new ResponseEntity<>(DataResponse.response(400,new ErrorData("NullPointerException","ERROR",e.getMessage())),HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<DataResponse<ErrorData>> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("MissingServletRequestParameterException : {}", e.getMessage());
        return new ResponseEntity<>(DataResponse.response(400,new ErrorData("HttpRequestMethodNotSupportedException","ERROR",e.getMessage())),HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> DBduplicateEntity(SQLIntegrityConstraintViolationException exception) {
        log.error("handleCustomException throw DataIntegrityViolationException : {} {}", exception.getStackTrace(), exception.getMessage());
        return ErrorResponse.toResponseEntity(DUPLICATE_ENTITY_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> allException(Exception e) {
        log.error("Exception : ",e);
        return ErrorResponse.toResponseEntity(UNKNOWN_SERVER_ERROR);
    }
}
