package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class APIPostAsyncVoterOcrResponse {
	private String status;
	@JsonProperty("request_id")
	private String requestId;
	@JsonProperty("group_id")
	private String groupId;
	@JsonProperty("task_id")
	private String taskId;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("completed_at")
	private String completedAt;
	private Float tat;
	@JsonProperty("ocr_output")
	private VoterOcrAsyncResult ocrOutput;
	private ErrorResult error;
	
		
}
