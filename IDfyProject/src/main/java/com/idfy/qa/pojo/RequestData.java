package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RequestData {
	@JsonProperty("doc_urls")
	private String[] docUrls;
	@JsonProperty("doc_url")
	private String docUrl;
	private String consent;

}
