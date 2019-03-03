package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AadharOcrAsyncResult {
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	private String address;
	private String district;
	@JsonProperty("fathers_name")
	private String fathersName;
	@JsonProperty("house_number")
	private String houseNumber;
	private String pincode;
	private String state;
	@JsonProperty("street_address")
	private String streetAddress;
	@JsonProperty("aadhaar_number")
	private String aadhaarNumber;
	private String gender;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	@JsonProperty("raw_text")
	private String rawText;
	@JsonProperty("year_of_birth")
	private String yearOfBirth;
	@JsonProperty("is_scanned")
	private String isScanned;
}
