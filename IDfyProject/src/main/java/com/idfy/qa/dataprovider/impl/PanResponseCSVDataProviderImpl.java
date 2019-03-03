package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostPanResponse;
import com.idfy.qa.pojo.PanCSVResponse;
import com.idfy.qa.pojo.PanResult;

public class PanResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public PanResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public PanCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<PanCSVResponse> map = mapper.readerFor(PanCSVResponse.class).with(schema).readValues(csvFile);
		ArrayList<PanCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new PanCSVResponse[0]);
	}

	@Override
	public APIPostPanResponse[] parseAllRows() throws IOException {
		PanCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostPanResponse> expectedResponses = new ArrayList();
		for (PanCSVResponse csvResponse : csvResponses) {
			APIPostPanResponse expectedResponse = new APIPostPanResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			PanResult result = new PanResult();
			result.setAge(csvResponse.getAge());
			result.setFathersName(csvResponse.getFathersName());
			result.setMinor(Boolean.parseBoolean(csvResponse.getMinor()));
			result.setGrayScale(Boolean.parseBoolean(csvResponse.getGrayScale()));
			result.setIdNumber(csvResponse.getIdNumber());
			result.setPanNumber(csvResponse.getPanNumber());
			result.setPanType(csvResponse.getPanType());
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setDateOnCard(csvResponse.getDateOnCard());
			result.setReferenceId(csvResponse.getReferenceId());
			result.setStatus(csvResponse.getResultStatus());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}

		return expectedResponses.toArray(new APIPostPanResponse[0]);
	}

}
