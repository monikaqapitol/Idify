package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostFaceValidationResponse;
import com.idfy.qa.pojo.FaceValidationResult;
import com.idfy.qa.pojo.FaceValidatonCSVResponse;

public class FaceValidationCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public FaceValidationCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public FaceValidatonCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<FaceValidatonCSVResponse> map = mapper.readerFor(FaceValidatonCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<FaceValidatonCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new FaceValidatonCSVResponse[0]);
	}

	@Override
	public APIPostFaceValidationResponse[] parseAllRows() throws IOException {
		FaceValidatonCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostFaceValidationResponse> expectedResponses = new ArrayList();
		for (FaceValidatonCSVResponse csvResponse : csvResponses) {
			APIPostFaceValidationResponse expectedResponse = new APIPostFaceValidationResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			FaceValidationResult result = new FaceValidationResult();
			result.setFaceDetected(csvResponse.getFaceDetected());
			result.setLivenessStatus(csvResponse.getLivenessStatus());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostFaceValidationResponse[0]);
	}

}
