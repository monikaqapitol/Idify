package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncAadharOcrResponse;
import com.idfy.qa.pojo.AadharOcrAsyncResult;
import com.idfy.qa.pojo.AsyncAadharCSVResponse;

public class AsyncAadharResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncAadharResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AsyncAadharCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsyncAadharCSVResponse> map = mapper.readerFor(AsyncAadharCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsyncAadharCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsyncAadharCSVResponse[0]);
	}

	@Override
	public APIPostAsyncAadharOcrResponse[] parseAllRows() throws IOException {
		AsyncAadharCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncAadharOcrResponse> expectedResponses = new ArrayList();
		for (AsyncAadharCSVResponse csvResponse : csvResponses) {
			APIPostAsyncAadharOcrResponse expectedResponse = new APIPostAsyncAadharOcrResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			AadharOcrAsyncResult result = new AadharOcrAsyncResult();
			result.setAadhaarNumber(csvResponse.getAadhaarNumber());
			result.setAddress(csvResponse.getAddress());
			result.setDateOfBirth(csvResponse.getDateOfBirth());
			result.setDistrict(csvResponse.getDistrict());
			result.setFathersName(csvResponse.getFathersName());
			result.setGender(csvResponse.getGender());
			result.setHouseNumber(csvResponse.getHouseNumber());
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setPincode(csvResponse.getPincode());
			result.setRawText(csvResponse.getRawText());
			result.setIsScanned(csvResponse.getIsScanned());
			result.setState(csvResponse.getState());
			result.setStreetAddress(csvResponse.getStreetAddress());
			result.setYearOfBirth(csvResponse.getYearOfBirth());
			expectedResponse.setOcrOutput(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncAadharOcrResponse[0]);
	}

}
