package com.lms.api.services.soapservices.apicall;

import com.lms.api.services.soapservices.customer.CustomerPort;
import com.lms.api.services.soapservices.customer.CustomerPortService;
import com.lms.api.services.soapservices.customer.CustomerRequest;
import com.lms.api.services.soapservices.customer.CustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

    CustomerPort customerPort;


    public CustomerPort getInstance(){

        CustomerPortService customerPortService = new CustomerPortService();

        CustomerPort customerPort =  customerPortService.getCustomerPortSoap11();

        return customerPort;
    }

    public CustomerResponse getKyc(String transactionId, String customerNumber){

        customerPort = getInstance();

        log.info("{}::: transactionId initiating the soap CALL to CBS to get customer kyc for customerNumber {}",transactionId, customerNumber);

        try {
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setCustomerNumber(customerNumber);

            return customerPort.customer(customerRequest);
        }
        catch (Exception e){
            log.error("{}:::transactionId error while making a soap call to get the customer kyc for customer Number {}",transactionId, customerNumber);

            return null;
        }
    }

}
