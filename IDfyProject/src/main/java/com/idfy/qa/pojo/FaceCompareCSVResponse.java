package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class FaceCompareCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String requestId;
	private String face1Quality;
	private String face1Status;
	private String face2Quality;
	private String face2Status;
	private String matchScore;
	private String matchStatus;
	private String reviewRequired;
	private String status;
	private String taskId;
	private String taskType;
	private String tat;
}
