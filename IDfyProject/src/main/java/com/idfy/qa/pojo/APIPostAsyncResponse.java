package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class APIPostAsyncResponse {

	private String status;
	@JsonProperty("request_id")
	private String requestId;

}
