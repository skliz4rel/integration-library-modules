package com.lms.api.models.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class APIError implements Serializable {

	private String statusMessage;

	private String timestamp;

	private String statusCode;

	private transient HttpStatus status;

}