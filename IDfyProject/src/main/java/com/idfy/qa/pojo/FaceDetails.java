package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FaceDetails {
	@JsonProperty("face_1")
	private Face face1;
	@JsonProperty("face_2")
	private Face face2;
}
