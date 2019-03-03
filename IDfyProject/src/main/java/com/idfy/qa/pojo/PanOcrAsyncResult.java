package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PanOcrAsyncResult {
	@JsonProperty("date_of_issue")
	private String dateOfIssue;
	private Integer age;
	@JsonProperty("date_on_card")
	private String dateOnCard;
	@JsonProperty("fathers_name")
	private String fathersName;
	@JsonProperty("is_scanned")
	private boolean isScanned;
	private boolean minor;
	@JsonProperty("name_on_card")
	private String nameOnCard;
	@JsonProperty("pan_number")
	private String panNumber;
	@JsonProperty("pan_type")
	private String panType;
	@JsonProperty("raw_text")
	private String rawText;

}
