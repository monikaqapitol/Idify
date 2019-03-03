package com.idfy.qa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AadharResult {
	@JsonProperty("ocr_output")
	private OCRResult ocrOutput;

}
