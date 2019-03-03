package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PanResult {
	private String age;
	@JsonProperty("date_on_card")
	private String dateOnCard;
	@JsonProperty("fathers_name")
	private String fathersName;
	@JsonProperty("gray_scale")
	private boolean grayScale;
	@JsonProperty("id_number")
	private String idNumber;
	@JsonProperty("pan_number")
	private String panNumber;
	@JsonProperty("reference_id")
	private String referenceId;
	private String status;
	@JsonProperty("date_of_issue")
	private String dateOfIssue;
	private boolean minor;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	@JsonProperty("pan_type")
	private String panType;
	@JsonProperty("is_scanned")
	private boolean iscanned;
	@JsonProperty("raw_text")
	private String Rawtext;
	

}
