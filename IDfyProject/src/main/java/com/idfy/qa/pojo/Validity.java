package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Validity {
	
	@JsonProperty("NT")
	private String nt;
	
}
