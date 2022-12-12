package com.lms.api.helpers;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionIdGenerator {

    public String generateTransactionId(String callingSystemName){

        String transactionId = null;

        String currentDateTime = "" + LocalDateTime.now().getSecond();

        String uuidData = callingSystemName + currentDateTime ;
        transactionId = new UUIDGenerator().getHashUuid(uuidData);

        return transactionId;
    }

}
