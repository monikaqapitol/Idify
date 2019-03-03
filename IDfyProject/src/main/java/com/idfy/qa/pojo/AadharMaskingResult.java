package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AadharMaskingResult {
	@JsonProperty("document_url")
	private String documentUrl;
	@JsonProperty("self_link")
	private String selfLink;
}
