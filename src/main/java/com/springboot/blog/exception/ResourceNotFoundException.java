package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) //we need to annotate this exception class with @ResponseStatus

public class ResourceNotFoundException extends RuntimeException  {
    private String resourceName;
    private String fieldName;
    private long fieldValue;
    //we need to provide the above parameters in exception message.

    //so we generate the constructors tow very parameter as below.

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        //in the below we need to pass a message to the superclass constructor using super keyword
        super(String.format("%s not found with %s:'%s'",resourceName,fieldName,fieldValue));   //it returns like (post not found with id=1)
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;

    }
    //we create  getter..and no need the setter..bcz we already initilize the fileds using constructor in the above.


    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}