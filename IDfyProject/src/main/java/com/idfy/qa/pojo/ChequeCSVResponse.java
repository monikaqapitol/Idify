package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class ChequeCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String requestId;
	private String accountName;
	private String accountNo;
	private String status;
	private String taskId;
	private String taskType;
	private String accountType;
	private String bankAddress;
	private String bankName;
	private String ifscCode;
}
