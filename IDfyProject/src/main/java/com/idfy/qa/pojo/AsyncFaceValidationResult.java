package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AsyncFaceValidationResult {
	@JsonProperty("face_detected")
	private boolean faceDetected;
	private boolean status;
}
