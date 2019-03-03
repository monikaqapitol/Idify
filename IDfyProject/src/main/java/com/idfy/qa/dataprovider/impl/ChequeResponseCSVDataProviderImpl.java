package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostChequeResponse;
import com.idfy.qa.pojo.ChequeCSVResponse;
import com.idfy.qa.pojo.ChequeResult;

public class ChequeResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public ChequeResponseCSVDataProviderImpl(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public ChequeCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<ChequeCSVResponse> map = mapper.readerFor(ChequeCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<ChequeCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new ChequeCSVResponse[0]);
	}

	@Override
	public APIPostChequeResponse[] parseAllRows() throws IOException {
		ChequeCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostChequeResponse> expectedResponses = new ArrayList();
		for (ChequeCSVResponse csvResponse : csvResponses) {
			APIPostChequeResponse expectedResponse = new APIPostChequeResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			ChequeResult result = new ChequeResult();
			result.setAccountName(csvResponse.getAccountName());
			result.setAccountNo(csvResponse.getAccountNo());
			result.setAccountType(csvResponse.getAccountType());
			result.setBankAddress(csvResponse.getBankAddress());
			result.setIfscCode(csvResponse.getIfscCode());
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostChequeResponse[0]);
	}

}
