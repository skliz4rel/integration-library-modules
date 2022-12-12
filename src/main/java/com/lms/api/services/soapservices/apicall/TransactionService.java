package com.lms.api.services.soapservices.apicall;

import com.lms.api.services.soapservices.transaction.TransactionDataPort;
import com.lms.api.services.soapservices.transaction.TransactionDataPortService;
import com.lms.api.services.soapservices.transaction.TransactionsRequest;
import com.lms.api.services.soapservices.transaction.TransactionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    TransactionDataPort transactionDataPort;

    public  TransactionDataPort getInstance(){

        TransactionDataPortService transactionDataPortService = new TransactionDataPortService();
        transactionDataPort = transactionDataPortService.getTransactionDataPortSoap11();

        return transactionDataPort;
    }


    public TransactionsResponse getTransactionData(String transactionId, String customerNumber){

        transactionDataPort = getInstance();

        log.info("{}::: transactionId initiating the transaction to soap CBS to get customer history for customerNumber {}",transactionId, customerNumber);

        TransactionsRequest request = new TransactionsRequest();
        request.setCustomerNumber(customerNumber);

        try {
            return transactionDataPort.transactions(request);
        }
        catch (Exception e)
        {
            log.error("{}:::transactionId error while making a request to get the transaction history for customer Number {}",transactionId, customerNumber);

            return null;
        }
    }


}
