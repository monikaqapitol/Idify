package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChequeOcrAsyncResult {
	@JsonProperty("account_name")
	private String accountName;
	@JsonProperty("account_no")
	private String accountNo;
	@JsonProperty("account_type")
	private String accountType;
	@JsonProperty("bank_address")
	private String bankAddress;
	@JsonProperty("bank_name")
	private String bankName;
	@JsonProperty("ifsc_code")
	private String ifscCode;
	@JsonProperty("raw_text")
	private String rawText;
}
