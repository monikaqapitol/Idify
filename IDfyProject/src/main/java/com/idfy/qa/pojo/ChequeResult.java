package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChequeResult {
	@JsonProperty("account_no")
	private String accountNo;
	@JsonProperty("account_type")
	private String accountType;
	@JsonProperty("account_name")
	private String accountName;
	@JsonProperty("bank_name")
	private String bankName;
	@JsonProperty("bank_address")
	private String bankAddress;
	@JsonProperty("ifsc_code")
	private String ifscCode;

}
