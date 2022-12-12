package com.lms.api.repositorys;

import com.lms.api.db.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, String> {

    public Mono<User> findByCustomerNumber(String customerNumber);

    public Mono<User> findByEmail(String email);

    public Mono<User> findByEmailOrCustomerNumber(String email, String customerNumber);

    public Flux<User> findByMonthlyIncome(double monthlyIncome);

    Mono<Boolean> existsByCustomerNumber(String customerNumber);
}
