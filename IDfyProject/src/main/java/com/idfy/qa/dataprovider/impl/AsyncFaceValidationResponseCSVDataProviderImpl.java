package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncFaceValidationResponse;
import com.idfy.qa.pojo.AsyncFaceValidationResult;
import com.idfy.qa.pojo.FaceValidatonCSVResponse;

public class AsyncFaceValidationResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncFaceValidationResponseCSVDataProviderImpl(String fileName) {
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
	public APIPostAsyncFaceValidationResponse[] parseAllRows() throws IOException {
		FaceValidatonCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncFaceValidationResponse> expectedResponses = new ArrayList();
		for (FaceValidatonCSVResponse csvResponse : csvResponses) {
			APIPostAsyncFaceValidationResponse expectedResponse = new APIPostAsyncFaceValidationResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			AsyncFaceValidationResult result = new AsyncFaceValidationResult();
			result.setFaceDetected(Boolean.parseBoolean(csvResponse.getFaceDetected()));
			result.setStatus(Boolean.parseBoolean(csvResponse.getLivenessStatus()));
			expectedResponse.setLiveness(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncFaceValidationResponse[0]);
	}

}
