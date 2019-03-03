package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostVoterResponse;
import com.idfy.qa.pojo.VoterCSVResponse;
import com.idfy.qa.pojo.VoterResult;

public class VoterResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public VoterResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public VoterCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<VoterCSVResponse> map = mapper.readerFor(VoterCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<VoterCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new VoterCSVResponse[0]);
	}

	@Override
	public APIPostVoterResponse[] parseAllRows() throws IOException {
		VoterCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostVoterResponse> expectedResponses = new ArrayList();
		for (VoterCSVResponse csvResponse : csvResponses) {
			APIPostVoterResponse expectedResponse = new APIPostVoterResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			VoterResult result = new VoterResult();
			result.setState(csvResponse.getState());
			result.setFathersName(csvResponse.getFathersName());
			result.setAddress(csvResponse.getAddress());
			result.setStreetAddress(csvResponse.getStreetAddress());
			result.setPincode(csvResponse.getPincode());
			result.setDistrict(csvResponse.getDistrict());
			result.setIdNumber(csvResponse.getIdNumber());
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setDateOfBirth(csvResponse.getDateOfBirth());
			result.setHouseNumber(csvResponse.getHouseNumber());
			result.setAge(csvResponse.getAge());
			result.setAgeAsOnYear(csvResponse.getAgeAsOnYear());
			result.setGender(csvResponse.getGender());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostVoterResponse[0]);
	}

}
