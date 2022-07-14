package io.github.kahar.task.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import io.github.kahar.task.app.error.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.UUID;

@ControllerAdvice
@Slf4j
class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {JsonPatchException.class, JsonProcessingException.class})
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        UUID uuid = UUID.randomUUID();
        String detail = "Problem with json patch uuid=" + uuid;
        log.info(detail, ex);
        ErrorDetail bodyOfResponse = ErrorDetail
                .builder()
                .title(String.valueOf(ex.getClass()))
                .status(status.value())
                .detail(detail)
                .instance((URI.create("urn:uuid:" + uuid)))
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), status, request);
    }
}