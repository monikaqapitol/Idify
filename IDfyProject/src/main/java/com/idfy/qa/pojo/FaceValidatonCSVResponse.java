package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class FaceValidatonCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String requestId;
	private String faceDetected;
	private String livenessStatus;
	private String status;
	private String taskId;
	private String taskType;
	private String tat;
}
