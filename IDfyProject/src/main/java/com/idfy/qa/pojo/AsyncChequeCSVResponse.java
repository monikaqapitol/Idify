package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class AsyncChequeCSVResponse {
	private String completedAt;
	private String createdAt;
	private String groupId;
	private String accountNo;
	private String accountType;
	private String bankAddress;
	private String bankName;
	private String ifscCode;
	private String rawText;
	private String requestId;
	private String status;
	private String taskId;
	private String tat;
	private String accountName;
}
