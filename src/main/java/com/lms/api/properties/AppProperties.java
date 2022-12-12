package com.lms.api.properties;

import io.netty.handler.logging.LogLevel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Data
public class AppProperties {

    /********************Scoring Engine API Properties *****************/

    private String scoringEngineBaseUri;

    private String registerEndpoint;

    private String initiateQueryScoreEndpoint;

    private String queryScoreEndpoint;

    /*****************************Soap Endpoints *****************************/

    private String customerSoapEndpoint;

    private String transactionSoapEndpoint;

    /******************Core Banking Credentials ***************/

    private String coreBankingUsername;

    private String coreBankingPassword;



    private String soapVersion;

    private int connectTimeout;

    private int readTimeout;

    private int responseTimeout;

    private boolean useConnectionPooling;

    private LogLevel logLevel = LogLevel.INFO;

    private String soapContentType = "xml";


}
