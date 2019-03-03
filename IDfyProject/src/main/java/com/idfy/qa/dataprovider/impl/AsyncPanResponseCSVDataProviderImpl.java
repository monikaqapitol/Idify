package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncPanOcrResponse;
import com.idfy.qa.pojo.AsynPanCSVResponse;
import com.idfy.qa.pojo.PanOcrAsyncResult;

public class AsyncPanResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncPanResponseCSVDataProviderImpl(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public AsynPanCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsynPanCSVResponse> map = mapper.readerFor(AsynPanCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsynPanCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsynPanCSVResponse[0]);
	}

	@Override
	public APIPostAsyncPanOcrResponse[] parseAllRows() throws IOException {
		AsynPanCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncPanOcrResponse> expectedResponses = new ArrayList();
		for (AsynPanCSVResponse csvResponse : csvResponses) {
			APIPostAsyncPanOcrResponse expectedResponse = new APIPostAsyncPanOcrResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			PanOcrAsyncResult result = new PanOcrAsyncResult();
			if(null != csvResponse.getAge() && !csvResponse.getAge().equalsIgnoreCase(""))
				result.setAge(Integer.parseInt(csvResponse.getAge()));
			result.setDateOfIssue(csvResponse.getDateOfIssue());
			result.setDateOnCard(csvResponse.getDateOnCard());
			result.setFathersName(csvResponse.getFathersName());
			result.setMinor(Boolean.parseBoolean(csvResponse.getMinor()));
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setPanNumber(csvResponse.getPanNumber());
			result.setPanType(csvResponse.getPanType());
			result.setRawText(csvResponse.getRawText());
			result.setScanned(Boolean.parseBoolean(csvResponse.getIsScanned()));
			expectedResponse.setOcrOutput(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncPanOcrResponse[0]);
	}

}
