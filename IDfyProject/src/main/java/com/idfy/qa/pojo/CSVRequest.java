package com.idfy.qa.pojo;

import lombok.Data;

@Data
public class CSVRequest {
	private String type;
	private String groupId;
	private String taskId;
	private String data;
	private String consent;
	private String gstin;

}
