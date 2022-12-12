package com.lms.api.services.dbservices;


import com.lms.api.db.Token;
import com.lms.api.repositorys.TokenRepository;
import com.lms.api.utils.EncodeDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TokenServiceImpl {

    private final TokenRepository tokenRepository;


    @Transactional
    public Mono<Boolean> storeCredentials(String transactionId, String username,String rawpassword, String hashPassword, String tokenkey){

        String basicAuthstr  = EncodeDecoder.encodeBase64(username+":"+rawpassword);

        try {

            //tokenRepository.deleteAll().block();

           return tokenRepository.findById("1").flatMap(item->{

               log.info("{}:::transactionId updating the token in db", transactionId);

                item.setUsername(username);
                item.setHashPassword(hashPassword);
                item.setScoringEngineToken(tokenkey);
                item.setBasicAuthstr(basicAuthstr);

                return tokenRepository.save(item).flatMap(it->{
                    if(it != null){
                        return Mono.just(true);
                    }
                    else
                        return Mono.just(false);
                });

            }).switchIfEmpty(
                    Mono.defer(() -> {
                       return saveToken(transactionId, username, rawpassword, hashPassword, tokenkey, basicAuthstr);
                    }
            ));
        }
        catch (Exception e){
            log.error("{}:::transactionId error was thrown saving the registered url and token to the database {}", transactionId, e);

            return Mono.just(false);
        }
    }

   Mono<Boolean> saveToken(String transactionId, String username,String rawpassword, String hashPassword, String tokenkey, String basicAuthstr){
        log.info("{} transactionId, saving the token in the database ", transactionId);

        Token tokenEntity = new Token();
        // This would prevent having multiple tokens
       tokenEntity.setId(randomId());
        tokenEntity.setUsername(username);
        tokenEntity.setHashPassword(hashPassword);
        tokenEntity.setScoringEngineToken(tokenkey);
        tokenEntity.setBasicAuthstr(basicAuthstr);


        return tokenRepository.save(tokenEntity).map((it)->{

            if(it != null) return true;
            else return false;
        });

    }


    private String randomId(){
        Random random = new Random();
       return  String.valueOf(random.nextInt(1000));
    }

}
