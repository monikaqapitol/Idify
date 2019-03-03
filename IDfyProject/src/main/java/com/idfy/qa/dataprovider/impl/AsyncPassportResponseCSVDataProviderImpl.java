package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncPassportOcrResponse;
import com.idfy.qa.pojo.AsyncPassportCSVResponse;
import com.idfy.qa.pojo.PassportOcrAsyncResult;

public class AsyncPassportResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncPassportResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AsyncPassportCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsyncPassportCSVResponse> map = mapper.readerFor(AsyncPassportCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsyncPassportCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsyncPassportCSVResponse[0]);
	}

	@Override
	public APIPostAsyncPassportOcrResponse[] parseAllRows() throws IOException {
		AsyncPassportCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncPassportOcrResponse> expectedResponses = new ArrayList();
		for (AsyncPassportCSVResponse csvResponse : csvResponses) {
			APIPostAsyncPassportOcrResponse expectedResponse = new APIPostAsyncPassportOcrResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			expectedResponse.setMessage(csvResponse.getMessage());
			PassportOcrAsyncResult result = new PassportOcrAsyncResult();
			result.setAddress(csvResponse.getAddress());
			result.setDateOfBirth(csvResponse.getDateOfBirth());
			result.setDateOfExpiry(csvResponse.getDateOfExpiry());
			result.setDateOfIssue(csvResponse.getDateOfIssue());
			result.setGivenName(csvResponse.getGivenName());
			result.setNameOfGuardian(csvResponse.getNameOfGuardian());
			result.setPassportNumber(csvResponse.getPassportNumber());
			result.setPlaceOfBirth(csvResponse.getPlaceOfBirth());
			result.setPlaceOfIssue(csvResponse.getPlaceOfIssue());
			result.setRawText(csvResponse.getRawText());
			expectedResponse.setOcrOutput(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncPassportOcrResponse[0]);
	}

}
