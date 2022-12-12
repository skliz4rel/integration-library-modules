package com.lms.api.http;

import com.lms.api.properties.AppProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class HttpClientUtil {


    @Autowired
    AppProperties webClientProperties;

    public HttpClient createInsecureHttpClient() throws SSLException {

        HttpClient httpClient = createCoreHttpClient();
        httpClient = httpClient.secure(sslContextSpec -> sslContextSpec.sslContext(generateInsecureContext()));

        return httpClient;
    }

    private HttpClient createCoreHttpClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.newConnection();
        LogLevel logLevel = webClientProperties.getLogLevel();
        if (null != webClientProperties && webClientProperties.getConnectTimeout() > 0 && webClientProperties.getConnectTimeout() > 0 && webClientProperties.getReadTimeout() > 0) {
            int connectionTimeout = webClientProperties.getConnectTimeout() * 1000;
            int readTimeout = webClientProperties.getReadTimeout() * 1000;
            Duration responseTimeout = Duration.ofMillis(webClientProperties.getResponseTimeout() * 1000);
            return HttpClient.create(connectionProvider).resolver(DefaultAddressResolverGroup.INSTANCE)
                    .wiretap("reactor.netty.http.client.HttpClient", logLevel, AdvancedByteBufFormat.TEXTUAL)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                    .responseTimeout(responseTimeout)
                    .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)))
                    .followRedirect(true);
        }
        return HttpClient.create(connectionProvider).resolver(DefaultAddressResolverGroup.INSTANCE)
                .wiretap("reactor.netty.http.client.HttpClient", logLevel, AdvancedByteBufFormat.TEXTUAL)
                .followRedirect(true);

    }


    public SslContext generateInsecureContext() {
        log.info("Generating Insecure Context Server Certifications : generateInsecureContext");
        try {
            return SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (SSLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
