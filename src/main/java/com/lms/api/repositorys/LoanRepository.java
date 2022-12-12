package com.lms.api.repositorys;


import com.lms.api.db.Loan;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LoanRepository extends ReactiveCrudRepository<Loan, String> {

    Mono<Boolean> existsByCustomerNumberAndIsActive(String customerNumber, boolean activeStatus);

    Mono<Boolean> existsByLoanIdAndIsActiveTrue(String loanId);

    Mono<Boolean> existsByCustomerNumberAndIsActiveTrue(String customerNumber);

    Mono<Void> deleteByLoanId(String loanId);
}