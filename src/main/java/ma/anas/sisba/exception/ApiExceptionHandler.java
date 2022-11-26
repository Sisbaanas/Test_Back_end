package ma.anas.sisba.exception;

//import io.sentry.Sentry;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static ma.anas.sisba.exception.PayLoadExceptionItem.HTTP_CALL_ERROR;
import static ma.anas.sisba.exception.PayLoadExceptionItem.INVALID_INPUT;


@RestControllerAdvice
@Log4j2
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ExceptionApi.class})
    public ResponseEntity<Object> payLoadException(final ExceptionApi ex) {
        //Sentry.captureException(ex);
        print(ex);
        return respondWithExceptionPayload(ex.getPayload());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnknownException(
            final Throwable ex,
            final WebRequest req
    ) {
        if (
                ex instanceof HttpMessageNotReadableException ||
                        ex instanceof MethodArgumentTypeMismatchException
        ) {
            return response(INVALID_INPUT, ex);
        } else if (ex instanceof RestClientException) {
            return response(HTTP_CALL_ERROR, ex);
        } else {
            //Sentry.captureException(ex);
            print(ex);
            return response(PayLoadExceptionItem.ERROR.message(ex.getMessage()), ex);
        }
    }

    private ResponseEntity<Object> response(
            PayLoadExceptionItem codeName,
            Throwable e
    ) {
        return response(
                codeName.getStatusCode(),
                codeName.getMessage(),
                codeName.getDetail(),
                e
        );
    }

    private ResponseEntity<Object> response(
            int status,
            String message,
            String detail,
            Throwable e
    ) {
        return respondWithExceptionPayload(
                Payload
                        .builder()
                        .statusCode(status)
                        .detail(detail)
                        .messageError(message)
                        .build()
        );
    }

    private ResponseEntity<Object> respondWithExceptionPayload(
            final Payload payload
    ) {
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }


    private void print(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }
}
