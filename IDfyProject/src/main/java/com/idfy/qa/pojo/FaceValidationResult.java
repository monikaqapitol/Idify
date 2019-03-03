package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FaceValidationResult {
	@JsonProperty("face_detected")
	private String faceDetected;
	@JsonProperty("liveness_status")
	private String livenessStatus;
}
