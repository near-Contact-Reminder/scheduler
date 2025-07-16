package kr.swyp.scheduler.common.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorInfo> handleNoSuchElementException(
            NoSuchElementException e) {
        return responseException("ELEMENT_NOT_FOUND", e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(
            IllegalArgumentException e) {
        log.info("입력값이 올바르지 않음: {}", e.getMessage());
        return responseException("BAD_REQUEST", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorInfo> handleMethodNotAllowedException(
            MethodNotAllowedException e) {
        return responseException("METHOD_NOT_ALLOWED", e.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().findFirst().map(
                DefaultMessageSourceResolvable::getDefaultMessage).orElse("입력값이 올바르지 않습니다.");
        log.info("입력값이 올바르지 않음: {}", e.getMessage());
        return responseException("VALIDATION_FAILED", message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorInfo> responseException(String code, String message,
            HttpStatus status) {
        ErrorInfo errorInfo = new ErrorInfo(code, message);
        return ResponseEntity.status(status).body(errorInfo);
    }

    private record ErrorInfo(String code, String message) {

    }
}
