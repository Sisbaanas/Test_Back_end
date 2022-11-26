package ma.anas.sisba.exception;

public enum PayLoadExceptionItem {

    SYSTEM_ERROR("API SYSTEM ERROR", 99),
    ERROR("", 100),
    INVALID_INPUT("INVALID INPUT", 101),
    HTTP_CALL_ERROR("Http call error", 102),
    RESOURCE_EXCEPTION("", 103),
    Label_Already_In_Use("Label already in use", 104),
    Email_Already_Used("Email already used ", 105),
    INCORRECT_CREDENTIALS("Login/Password not match !", 110),
    DENIED("Access denied !", 111),
    DISABLED_ACCOUNT("this account is disabled ! ", 112),

    ;

    private String message;
    private String detail;
    private Integer statusCode;

    PayLoadExceptionItem(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public PayLoadExceptionItem detail(String d) {
        this.detail = d;
        return this;
    }

    public PayLoadExceptionItem message(String m) {
        this.message = m;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return (
                "PayLoadExceptionItem{" +
                        "message='" +
                        message +
                        '\'' +
                        ", detail='" +
                        detail +
                        '\'' +
                        ", statusCode=" +
                        statusCode +
                        '}'
        );
    }
}
