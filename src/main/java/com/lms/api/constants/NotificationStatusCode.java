package com.lms.api.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Slf4j
@Getter
public enum NotificationStatusCode {

    OK("0000", "Success.", HttpStatus.OK),
        CREATED("0000", "Created", HttpStatus.CREATED),

    HTTP_CONNECTION_ERROR(
            "2000", "Could not establish a connection to the server.", HttpStatus.SERVICE_UNAVAILABLE),

    CLIENT_BAD_REQUEST("400", "Bad request", HttpStatus.BAD_REQUEST),
    CLIENT_UNAUTHORIZED("401", "Unauthorized", HttpStatus.UNAUTHORIZED),

    CLIENT_FORBIDDEN("403", "Forbidden", HttpStatus.FORBIDDEN),
    SERVICE_NOT_FOUND("404", "Not found", HttpStatus.NOT_FOUND),
    SERVICE_METHOD_NOT_ALLOWED("405", "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    SERVICE_NOT_ACCEPTABLE("406", "Not acceptable", HttpStatus.NOT_ACCEPTABLE),
    SERVICE_REQUEST_TIMEOUT("408", "Request timeout", HttpStatus.REQUEST_TIMEOUT),
    SERVICE_CONFLICT("409", "Conflict", HttpStatus.CONFLICT),
    SERVICE_GONE("410", "Gone", HttpStatus.GONE),
    SERVICE_PRECONDITION_FAILED("412", "Precondition failed", HttpStatus.PRECONDITION_FAILED),
    SERVICE_PAYLOAD_TOO_LARGE("413", "Payload too large", HttpStatus.PAYLOAD_TOO_LARGE),
    SERVICE_LONG_URI("414", "URI too long", HttpStatus.URI_TOO_LONG),
    UNSUPPORTED_MEDIATYPE("415", "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    SERVICE_FAILED_EXP("417", "Expectation failed", HttpStatus.EXPECTATION_FAILED),

    DOWNSTREAM_SERVER_ERROR("500", "Downstream server returned an internal server error", HttpStatus.NOT_FOUND),

    DOWNSTREAM_GATEWAY_TIMEOUT("502", "Downstream server unavailable to handle the request", HttpStatus.NOT_FOUND),

    DOWNSTREAM_OK("0", "Success (Submit Success)", HttpStatus.OK);


    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
    NotificationStatusCode(final String code, final String description, final HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    @JsonCreator
    public static NotificationStatusCode getEnum(String value) {
        for (NotificationStatusCode v : values()) {
            if (Integer.parseInt(v.getCode()) == Integer.parseInt(value)) {
                return v;
            }
        }
        for (NotificationStatusCode v : values()) {
            if (v.getHttpStatus().value() == Integer.parseInt(value)) {
                return v;
            }
        }

        throw new IllegalArgumentException("Invalid Code Sent By Microservice: " + value);
    }

    public static Optional<NotificationStatusCode> resolveForCode(String code) {
        for (NotificationStatusCode value : NotificationStatusCode.values()) {
            if (code.equals(value.getCode())) {

                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public static NotificationStatusCode resolveCode(String code) {
        log.info("----->> Resolving response code returned from downstream/external system: " + code);
        for (NotificationStatusCode value : NotificationStatusCode.values()) {
            if (code.equals(value.getCode())) {

                log.info("----->> Response code exists in MADAPI's canonical code mapping: " +
                        value.getCode() + " : " + value.getDescription() + " : " + value.getHttpStatus());
                return value;
            }
        }

        log.info("----->> Response code does NOT exist in MADAPI canonical code mapping: " + code +
                " : resorting to MADAPI's INTERNAL SERVER ERROR");

        return NotificationStatusCode.DOWNSTREAM_GATEWAY_TIMEOUT;
    }

    public static NotificationStatusCode resolveForHttpStatus(int httpStatus){
        for (NotificationStatusCode value : NotificationStatusCode.values()) {
            if (httpStatus == value.httpStatus.value()) {
                return value;
            }
        }
        return NotificationStatusCode.DOWNSTREAM_GATEWAY_TIMEOUT;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    private static class Constants {

        public static final String SYSTEM_ERROR = "System error";
    }
}
