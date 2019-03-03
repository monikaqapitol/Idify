package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IssueDates {
	@JsonProperty("TRANS")
	private String trans;
	@JsonProperty("PSVBUS")
	private String psvbus;
	@JsonProperty("LMV")
	private String lmv;
	@JsonProperty("MCWG")
	private String mcwg;
	@JsonProperty("LMV-TR")
	private String lmvtr;
	@JsonProperty("3W-TR")
	private String wtr;
}
