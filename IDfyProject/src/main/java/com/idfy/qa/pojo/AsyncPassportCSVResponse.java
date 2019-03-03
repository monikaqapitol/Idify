package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class AsyncPassportCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String address;
	private String dateOfBirth;
	private String dateOfExpiry;
	private String dateOfIssue;
	private String givenName;
	private String nameOfGuardian;
	private String passportNumber;
	private String placeOfBirth;
	private String placeOfIssue;
	private String rawText;
	private String requestId;
	private String status;
	private String taskId;
	private String tat;
	private String message;
}
