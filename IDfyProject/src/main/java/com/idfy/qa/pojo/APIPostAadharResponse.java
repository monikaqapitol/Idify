package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "request_id", "created_at", "completed_at" })
public class APIPostAadharResponse {
	private String status;
	@JsonProperty("request_id")
	private String requestId;
	@JsonProperty("task_type")
	private String taskType;
	@JsonProperty("group_id")
	private String groupId;
	@JsonProperty("task_id")
	private String taskId;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("completed_at")
	private String completedAt;
	private AadharResult result;
	private ErrorResult error;

}
