package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VoterOcrAsyncResult {
	private String address;
	private String age;
	@JsonProperty("age_as_on_year")
	private String ageAsOnYear;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	@JsonProperty("date_on_card")
	private String dateOnCard;
	@JsonProperty("district_name")
	private String districtName;
	@JsonProperty("fathers_name")
	private String fathersName;
	private String gender;
	@JsonProperty("house_number")
	private String houseNumber;
	@JsonProperty("voter_number")
	private String voterNumber;
	@JsonProperty("raw_text")
	private String rawText;
	@JsonProperty("state_name")
	private String stateName;
	
	@JsonProperty("name_on_card")
	private String nameOnCard;
	
	private String pincode;
	private String state;
	@JsonProperty("street_address")
	private String streetAddress;
	@JsonProperty("year_of_birth")
	private String yearOfBirth;
}
