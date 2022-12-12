package com.lms.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//https://mkyong.com/java/jackson-how-to-parse-json/

@Slf4j
@Component
public class Jsonutility {

    public  String ConvertTojson(Object myobject){

        String json = "";

        try {
            //Gson gson = new Gson();
            ///String json = new GsonBuilder().create().toJson(myobject);

            json  = new ObjectMapper().writeValueAsString(myobject);
        }
        catch (Exception e){
            log.error("Error converting object to json {} {}", e.getCause(), e.getMessage());
        }
        return json;
    }

    public  <T> T fromjsonToObject(String json, final Class<T> objectClass){

       // Gson gson = new Gson();
        // return  gson.fromJson(json, objectClass);

        T responseObj = null;

        try {
           responseObj =  new ObjectMapper().readValue(json, objectClass);
        }
        catch (Exception e){
            log.error("Error parsing the json {} {}",e.getCause(), e.getMessage());
        }

        return responseObj;
    }

}