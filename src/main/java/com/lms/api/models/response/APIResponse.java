package com.lms.api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.api.models.error.APIError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class APIResponse<T>  extends RepresentationModel implements Serializable {

    private String statusCode;

    private APIError error;

    private T data;

    private String statusMessage;

    private String transactionId;

    private String timeStamp;

    private String method;


    /*
    @NotNull(message="example: 0000")
    private String statusCode;

    @NotNull(message="example: 01235")
    private String statusMessage;

    private String transactionId;

    private T data;

    private APIError error;*/
}
