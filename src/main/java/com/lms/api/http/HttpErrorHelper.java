package com.lms.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.models.error.APIError;
import com.lms.api.models.response.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class HttpErrorHelper {

    public Mono<APIResponse> returnErrResponse(String transactionId, Exception e, String code, String description, HttpStatus status){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMethod(HttpMethod.POST.name());
        apiResponse.setTransactionId(transactionId);

        APIError error = new APIError();
        error.setTimestamp(LocalDateTime.now().toString());

        Map<String, Object> apiWebclientError  = null;

        if (e != null && (e instanceof WebClientResponseException)) {
            log.error("{} ::: transactionId Error while making api call to the service {}",transactionId, e);

            var ex = (WebClientResponseException) e;
            var mapper = new ObjectMapper();

            try {
                apiWebclientError = mapper.readValue(ex.getResponseBodyAsString(), Map.class);
            }
            catch ( IOException e1) {
                log.error("{} ::: transactionId error mapping json parseing exception {}",transactionId, e1);
            }

            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());  //Since the failure is coming from the 3rd party service. We take it as a bad request cosing the error.
            apiResponse.setStatusMessage("Unsuccessful call 3rd party service failed");
            error.setStatusCode(apiWebclientError.get("statusCode").toString());
            error.setStatusMessage("Error from 3rd party webservice:: "+apiWebclientError.get("supportMessage").toString());

        }
        else{
            log.error("{} ::: transactionId application failed at this point ");

            apiResponse.setStatusCode(code);
            apiResponse.setStatusMessage("Failure probably an internal failure in LMS: Report to Administrator");

            error.setStatusCode(code);
            error.setStatusMessage(description);
        }

        apiResponse.setData(error);

        return Mono.just(apiResponse);
    }


}
