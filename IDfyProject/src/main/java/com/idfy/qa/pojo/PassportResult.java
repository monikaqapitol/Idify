package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PassportResult {
	@JsonProperty("id_number")
	private String idNumber;
	private String name;
	@JsonProperty("place_of_birth")
	private String placeOfBirth;
	@JsonProperty("place_of_issue")
	private String placeOfIssue;
	private String address;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	@JsonProperty("date_of_issue")
	private String dateOfIssue;
	@JsonProperty("name_of_guardian")
	private String nameOfGuardian;
	@JsonProperty("date_of_expiry")
	private String dateOfExpiry;
}
