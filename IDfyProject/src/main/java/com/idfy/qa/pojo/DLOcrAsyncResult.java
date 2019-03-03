package com.idfy.qa.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class DLOcrAsyncResult {
	private String address;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	@JsonProperty("fathers_name")
	private String fathersName;
	@JsonProperty("house_number")
	private String houseNumber;
	@JsonProperty("dl_number")
	private String dlNumber;
	@JsonProperty("is_scanned")
	private String isScanned;
	@JsonProperty("date_of_issue")
	private String dateOfIssue;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	private String pincode;
	private String state;
	@JsonProperty("street_address")
	private String streetAddress;
	private ArrayList<String> type;
	@JsonProperty("issue_dates")
	private IssueDates issueDates;
	private Validity validity;
	@JsonProperty("raw_text")
	private String rawText;
	
}
