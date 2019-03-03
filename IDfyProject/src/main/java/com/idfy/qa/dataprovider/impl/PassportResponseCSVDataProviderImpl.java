package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostPassportResponse;
import com.idfy.qa.pojo.PassportCSVResponse;
import com.idfy.qa.pojo.PassportResult;

public class PassportResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public PassportResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public PassportCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<PassportCSVResponse> map = mapper.readerFor(PassportCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<PassportCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new PassportCSVResponse[0]);
	}

	@Override
	public APIPostPassportResponse[] parseAllRows() throws IOException {
		PassportCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostPassportResponse> expectedResponses = new ArrayList();
		for (PassportCSVResponse csvResponse : csvResponses) {
			APIPostPassportResponse expectedResponse = new APIPostPassportResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getTaskType());
			PassportResult result = new PassportResult();
			result.setIdNumber(csvResponse.getIdNumber());
			result.setName(csvResponse.getName());
			result.setPlaceOfBirth(csvResponse.getPlaceOfBirth());
			result.setAddress(csvResponse.getAddress());
			result.setNameOfGuardian(csvResponse.getNameOfGuardian());
			result.setPlaceOfIssue(csvResponse.getPlaceOfIssue());
			result.setDateOfExpiry(csvResponse.getDateOfExpiry());
			result.setPlaceOfIssue(csvResponse.getPlaceOfIssue());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostPassportResponse[0]);
	}

}
