package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PassportOcrAsyncResult {
	private String address;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	@JsonProperty("date_of_expiry")
	private String dateOfExpiry;
	@JsonProperty("date_of_issue")
	private String dateOfIssue;
	@JsonProperty("given_name")
	private String givenName;
	@JsonProperty("name_of_guardian")
	private String nameOfGuardian;
	@JsonProperty("passport_number")
	private String passportNumber;
	@JsonProperty("place_of_birth")
	private String placeOfBirth;
	@JsonProperty("place_of_issue")
	private String placeOfIssue;
	@JsonProperty("raw_text")
	private String rawText;
}
