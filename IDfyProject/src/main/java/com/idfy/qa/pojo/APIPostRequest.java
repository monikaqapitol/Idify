package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class APIPostRequest {

	private String type;
	@JsonProperty("group_id")
	private String groupId;
	@JsonProperty("task_id")
	private String taskId;
	private RequestData data;

}
