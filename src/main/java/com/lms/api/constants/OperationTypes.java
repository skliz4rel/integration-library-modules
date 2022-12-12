package com.lms.api.constants;


public enum OperationTypes {

    SUBSCRIBE("Subscribe"),
    REGISTER("Register"),
    GET_KYC("GetKycdata"),
    LOANREQUEST("Loanrequest"),
    LOANSTATUS("Loanstatus");

    private final String name;

    OperationTypes(String name){
        this.name = name;
    }

    public String getName(){

        return name;
    }
}
