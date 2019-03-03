package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VoterResult {
	private String address;
	
	@JsonProperty("voter_number")
	private String voterNumber;
	
	private String age;
	@JsonProperty("age_as_on_year")
	private String ageAsOnYear;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	private String district;
	@JsonProperty("fathers_name")
	private String fathersName;
	private String gender;
	@JsonProperty("house_number")
	private String houseNumber;
	@JsonProperty("id_number")
	private String idNumber;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	private String pincode;
	private String state;
	@JsonProperty("street_address")
	private String streetAddress;
	@JsonProperty("year_of_birth")
	private String yearOfBirth;

}
