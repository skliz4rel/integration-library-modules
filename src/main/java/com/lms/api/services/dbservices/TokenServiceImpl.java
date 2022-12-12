package com.lms.api.services.dbservices;


import com.lms.api.db.Token;
import com.lms.api.repositorys.TokenRepository;
import com.lms.api.utils.EncodeDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TokenServiceImpl {

    private final TokenRepository tokenRepository;


    public Mono<Boolean> storeCredentials(String transactionId, String username,String rawpassword, String hashPassword, String tokenkey){

        String basicAuthstr  = EncodeDecoder.encodeBase64(username+":"+rawpassword);

        try {

            Token tokenEntity = new Token();
         // This would prevent having multiple tokens
            tokenEntity.setUsername(username);
            tokenEntity.setHashPassword(hashPassword);
            tokenEntity.setScoringEngineToken(tokenkey);
            tokenEntity.setBasicAuthstr(basicAuthstr);

            tokenRepository.deleteAll().block();

            return  tokenRepository.save(tokenEntity).map(it->{

                        if(it !=null) return true;
                        else
                        return false;
                    })
                    .onErrorResume((ex) -> {


                        log.error("{} :::transactionId error saving credentials in the register operations error:: {}", transactionId, ex);
                        return Mono.just(false);
                    });
        }
        catch (Exception e){
            log.error("{}:::transactionId error was thrown saving the registered url and token to the database {}", transactionId, e);

            return Mono.just(false);
        }
    }


}
