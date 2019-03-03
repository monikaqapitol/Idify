package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAadharMaskingResponse;
import com.idfy.qa.pojo.AadharMaskingCSVResponse;
import com.idfy.qa.pojo.AadharMaskingResult;

public class AadharMaskingResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AadharMaskingResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AadharMaskingCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AadharMaskingCSVResponse> map = mapper.readerFor(AadharMaskingCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AadharMaskingCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AadharMaskingCSVResponse[0]);
	}

	@Override
	public APIPostAadharMaskingResponse[] parseAllRows() throws IOException {
		AadharMaskingCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAadharMaskingResponse> expectedResponses = new ArrayList();
		for (AadharMaskingCSVResponse csvResponse : csvResponses) {
			APIPostAadharMaskingResponse expectedResponse = new APIPostAadharMaskingResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			AadharMaskingResult result = new AadharMaskingResult();
			result.setDocumentUrl(csvResponse.getDocumentUrl());
			result.setSelfLink(csvResponse.getSelfLink());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAadharMaskingResponse[0]);
	}

}
