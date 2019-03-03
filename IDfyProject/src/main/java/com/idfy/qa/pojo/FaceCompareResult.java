package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FaceCompareResult {
	@JsonProperty("face_details")
	private FaceDetails faceDetails;
	@JsonProperty("match_score")
	private Float matchScore;
	@JsonProperty("match_status")
	private String matchStatus;
	@JsonProperty("review_required")
	private String reviewRequired;
}
