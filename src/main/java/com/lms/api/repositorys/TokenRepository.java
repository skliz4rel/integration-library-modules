package com.lms.api.repositorys;

import com.lms.api.db.Token;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends R2dbcRepository<Token, String> {

    public Mono<Token> findByUsername(String username);
    public Mono<Token> findByUsernameAndHashPassword(String username, String hashpass);
    public Mono<Token> findByBasicAuthstr(String basicAuthstr);
}
