package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OCRResult {
	private String address;
	@JsonProperty("fathers_name")
	private String fathersName;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	private String gender;
	@JsonProperty("is_scanned")
	private String isScanned;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	@JsonProperty("year_of_birth")
	private String yearOfBirth;
	@JsonProperty("house_number")
	private String houseNumber;
	@JsonProperty("id_number")
	private String idNumber;
	private String pincode;
	private String state;
	private String district;
	@JsonProperty("street_address")
	private String streetAddress;
}
