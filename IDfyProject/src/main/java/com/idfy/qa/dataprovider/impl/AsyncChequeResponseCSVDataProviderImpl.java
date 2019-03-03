package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncChequeOcrResponse;
import com.idfy.qa.pojo.AsyncChequeCSVResponse;
import com.idfy.qa.pojo.ChequeOcrAsyncResult;

public class AsyncChequeResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncChequeResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AsyncChequeCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsyncChequeCSVResponse> map = mapper.readerFor(AsyncChequeCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsyncChequeCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsyncChequeCSVResponse[0]);
	}

	@Override
	public APIPostAsyncChequeOcrResponse[] parseAllRows() throws IOException {
		AsyncChequeCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncChequeOcrResponse> expectedResponses = new ArrayList();
		for (AsyncChequeCSVResponse csvResponse : csvResponses) {
			APIPostAsyncChequeOcrResponse expectedResponse = new APIPostAsyncChequeOcrResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			ChequeOcrAsyncResult result = new ChequeOcrAsyncResult();
			result.setAccountName(csvResponse.getAccountName());
			result.setAccountNo(csvResponse.getAccountNo());
			result.setAccountType(csvResponse.getAccountType());
			result.setBankAddress(csvResponse.getBankAddress());
			result.setBankName(csvResponse.getBankName());
			result.setIfscCode(csvResponse.getIfscCode());
			result.setRawText(csvResponse.getRawText());
			expectedResponse.setOcrOutput(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAsyncChequeOcrResponse[0]);
	}

}
