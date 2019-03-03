package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AsyncRequestData {
	@JsonProperty("doc_url")
	private String[] docUrl;
	@JsonProperty("aadhaar_consent")
	private String aadharConsent;
	private String gstin;

}
