package com.lms.api.services.scoringapicalls;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.constants.NotificationStatusCode;
import com.lms.api.http.HttpErrorHelper;
import com.lms.api.http.WebclientUtility;
import com.lms.api.models.error.APIError;
import com.lms.api.models.response.APIResponse;
import com.lms.api.models.response.KycscoreResponse;
import com.lms.api.properties.AppProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ScoringengineService {

    private final AppProperties appProperties;

    private final WebclientUtility webclientUtility;

    private final HttpErrorHelper httpErrorHelper;


    public Mono<APIResponse> initiateQueryScore_returnKycScore(String transactionId, String scoringEngineToken, String customerNumber){

        Map<String, String> headers = new HashMap<>();
        headers.put("client-token", scoringEngineToken);

        String fulluri = appProperties.getScoringEngineBaseUri()+appProperties.getInitiateQueryScoreEndpoint()+customerNumber;

        try {

          return  this.webclientUtility.get(
                    fulluri,
                    headers, String.class)
                    .cast(String.class)
                    .map(token -> {

                        if(token != null){

                            log.info("{} ::: transactionId, Scoring engine return token for Kyc request for customer number {}", transactionId, customerNumber);

                              return getKYCScore( transactionId, headers, customerNumber, String.valueOf(token));
                            }
                        else {
                            log.info("{}::: transactionId, Scoring engine return a null token for Customer Number {} while making an attempt to initiate kyc score query {}", transactionId, customerNumber, token);

                            return Mono.empty();
                        }
                    });
        }
        catch (WebClientResponseException | IOException ex){

            return httpErrorHelper.returnErrResponse(transactionId, ex,
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getCode(),
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getDescription(),
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getHttpStatus() );
        }
    }

    public Mono<APIResponse> getKYCScore(String transactionId, Map<String, String> headers,String customerNumber,String token) {

        log.info("{}:::transactionId initiating restful call to get the kyc score from scoring engine for customer Number {}", transactionId, customerNumber);


        String fulluri = appProperties.getScoringEngineBaseUri()+appProperties.getInitiateQueryScoreEndpoint()+token;

        try {
            return this.webclientUtility.get(fulluri, headers,KycscoreResponse.class)
                    .cast(KycscoreResponse.class)
                    .map(response -> {
                        APIResponse apiResponse = new APIResponse();
                        apiResponse.setStatusCode(NotificationStatusCode.OK.getCode());
                        apiResponse.setStatusMessage(NotificationStatusCode.OK.getDescription());

                        apiResponse.setData(response);

                        return apiResponse;
                    })
                    .onErrorResume((ex) -> {

                        log.error("{}:::transactionId error when getting scoring token request {}", transactionId, ex);

                        WebClientResponseException webExcep = null;

                        if(ex instanceof  WebClientResponseException){
                            webExcep = (WebClientResponseException) ex;
                        }

                        return httpErrorHelper.returnErrResponse(transactionId, webExcep,
                                NotificationStatusCode.HTTP_CONNECTION_ERROR.getCode(),
                                NotificationStatusCode.HTTP_CONNECTION_ERROR.getDescription(),
                                NotificationStatusCode.HTTP_CONNECTION_ERROR.getHttpStatus() );
                    });
        }
        catch (WebClientResponseException | IOException ex){

            return httpErrorHelper.returnErrResponse(transactionId, ex,
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getCode(),
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getDescription(),
                    NotificationStatusCode.HTTP_CONNECTION_ERROR.getHttpStatus() );

        }
    }


}