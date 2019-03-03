package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncVoterOcrResponse;
import com.idfy.qa.pojo.AsynVoterCSVResponse;
import com.idfy.qa.pojo.VoterOcrAsyncResult;

public class AsyncVoterResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncVoterResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AsynVoterCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsynVoterCSVResponse> map = mapper.readerFor(AsynVoterCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsynVoterCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsynVoterCSVResponse[0]);
	}

	@Override
	public APIPostAsyncVoterOcrResponse[] parseAllRows() throws IOException {
		AsynVoterCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncVoterOcrResponse> expectedResponses = new ArrayList();
		for (AsynVoterCSVResponse csvResponse : csvResponses) {
			APIPostAsyncVoterOcrResponse expectedResponse = new APIPostAsyncVoterOcrResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			VoterOcrAsyncResult result = new VoterOcrAsyncResult();
			result.setStateName(csvResponse.getStateName());
			result.setFathersName(csvResponse.getFathersName());
			result.setAddress(csvResponse.getAddress());
			result.setStreetAddress(csvResponse.getStreetAddress());
			result.setPincode(csvResponse.getPincode());
			result.setDistrictName(csvResponse.getDistrictName());
			result.setVoterNumber(csvResponse.getVoterNumber());
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setDateOfBirth(csvResponse.getDateOnCard());
			result.setHouseNumber(csvResponse.getHouseNumber());
			result.setAge(csvResponse.getAge());
			result.setAgeAsOnYear(csvResponse.getAgeAsOnYear());
			result.setGender(csvResponse.getGender());
			result.setAge(csvResponse.getAge());
			result.setRawText(csvResponse.getRawText());
			expectedResponse.setOcrOutput(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncVoterOcrResponse[0]);
	}

}
