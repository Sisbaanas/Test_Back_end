package ma.anas.sisba.exception;

public class ExceptionApi extends RuntimeException {
    private final Payload payload = new Payload();

    public ExceptionApi() {
        // empty constructor
    }

    public ExceptionApi(Payload payload) {
        super(payload.getMessageError());
        this.payload.setMessageError(payload.getMessageError());
        this.payload.setDetail(payload.getDetail());
        this.payload.setResponseCode(payload.getResponseCode());
    }

    public ExceptionApi(
            int code,
            final String messageError,
            final String detail
    ) {
        super(messageError);
        this.payload.setMessageError(messageError);
        this.payload.setResponseCode(code);
        this.payload.setDetail(detail);
    }

    public ExceptionApi(
            int code,
            final String messageError,
            final String detail,
            Throwable throwable
    ) {
        super(messageError, throwable);
        this.payload.setMessageError(messageError);
        this.payload.setResponseCode(code);
        this.payload.setDetail(detail);
    }

    public ExceptionApi(String messageError) {
        super(messageError);
        this.payload.setMessageError(messageError);
        this.payload.setResponseCode(0);
        this.payload.setDetail(null);
    }

    public ExceptionApi(PayLoadExceptionItem payload) {
        super(payload.getMessage());
        this.payload.setMessageError(payload.getMessage());
        this.payload.setResponseCode(payload.getStatusCode());
        this.payload.setDetail(payload.getDetail());
    }

    public Payload getPayload() {
        return payload;
    }
}
