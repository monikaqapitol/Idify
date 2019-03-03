package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class PassportCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String requestId;
	private String address;
	private String dateOfBirth;
	private String dateOfExpiry;
	private String dateOfIssue;
	private String idNumber;
	private String name;
	private String nameOfGuardian;
	private String placeOfBirth;
	private String placeOfIssue;
	private String status;
	private String taskId;
	private String taskType;
}
