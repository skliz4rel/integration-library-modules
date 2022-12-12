package com.lms.api.services.dbservices;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.lms.api.db.Loan;
import com.lms.api.repositorys.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class LoanServiceImpl {

    private LoanRepository loanRepository;

    public Mono<String> saveLoan_returnID(String transactionId, String customerNumber, double amount, String score){

        try {
           return loanRepository.existsByCustomerNumberAndIsActiveTrue(customerNumber)
                    .flatMap(check->{
                        String loanId  = UUID.randomUUID().toString();

                       if(check){
                           Loan loan =new Loan();
                          // loan.setId(loanId);

                           loan.setLoanId(loanId);
                           loan.setActive(true);
                           loan.setCreditscore(score);
                           loan.setInterest(0);
                           loan.setPaidback(false);
                           loan.setPaybackAmount(amount);
                           loan.setInterest(0);
                           loan.setCreatedDate(LocalDate.now());
                           loan.setCreatedTime(LocalTime.now());
                           loan.setPaybackDate(LocalDate.now().plusDays(10));

                           loanRepository.save(loan);

                           return Mono.just(loanId);
                       }
                       else{

                           return Mono.just(loanId);
                       }
                    });
        }
        catch (Exception e){
            log.error("{}::::transactionID, Error saving the loan request {}");

            return Mono.just("");
        }
    }


}
