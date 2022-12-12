package com.lms.api.services.dbservices;

import com.lms.api.db.User;
import com.lms.api.helpers.TransactionIdGenerator;
import com.lms.api.repositorys.UserRepository;
import com.lms.api.services.soapservices.customer.Customer;
import com.lms.api.services.soapservices.customer.CustomerResponse;
import com.lms.api.utils.DateHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public Mono<Boolean> storeUser(String transactionId, CustomerResponse customerResponse){

        Customer customer = customerResponse.getCustomer();

        LocalDate dob = LocalDate.now();

        if(customer.getDob() != null){
            dob = DateHelper.getDateFromXmlGregCalender(customer.getDob());
        }

        try {

            LocalDate finalDob = dob;
          return  userRepository.findByEmailOrCustomerNumber(customer.getEmail(), customer.getCustomerNumber())
                    .map(item -> {

                        item = setValuesInUserobj(item, finalDob, customer);

                        userRepository.save(item);

                        return true;
                    })
                    .switchIfEmpty(Mono.fromCallable(() -> {
                        User user = setValuesInUserobj(new User(), finalDob, customer);

                        this.userRepository.save(user);

                        return true;
                    }));
        }
        catch (Exception e){

            log.error("{} ::: transactionId error while saving the user in the database {}", transactionId, e);

            return Mono.just(false);
        }

    }


    public User setValuesInUserobj(User user,LocalDate dob, Customer customer){

        user.setId(customer.getId()+"");
        user.setDob(dob);
        user.setEmail(customer.getEmail().toLowerCase());
        user.setCustomerNumber(customer.getCustomerNumber());
        user.setGender(customer.getGender());
        user.setIdNumber(customer.getIdNumber());
        user.setIdType(customer.getIdType());
        user.setLastName(customer.getLastName().toLowerCase());
        user.setFirstName(customer.getFirstName().toLowerCase());
        user.setMobile(customer.getMobile());
        user.setMonthlyIncome(customer.getMonthlyIncome());
        user.setMiddleName(customer.getMiddleName().toLowerCase());
        user.setStatus(customer.getStatus());

        return user;
    }

}
