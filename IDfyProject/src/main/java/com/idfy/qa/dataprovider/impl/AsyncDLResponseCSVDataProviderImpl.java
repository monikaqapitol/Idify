package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncDLOcrResponse;
import com.idfy.qa.pojo.AsyncDLCSVResponse;
import com.idfy.qa.pojo.DLOcrAsyncResult;
import com.idfy.qa.pojo.IssueDates;
import com.idfy.qa.pojo.Validity;

public class AsyncDLResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncDLResponseCSVDataProviderImpl(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public AsyncDLCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AsyncDLCSVResponse> map = mapper.readerFor(AsyncDLCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AsyncDLCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AsyncDLCSVResponse[0]);
	}

	@Override
	public APIPostAsyncDLOcrResponse[] parseAllRows() throws IOException {
		AsyncDLCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAsyncDLOcrResponse> expectedResponses = new ArrayList();
		for (AsyncDLCSVResponse csvResponse : csvResponses) {
			APIPostAsyncDLOcrResponse expectedRepsonse = new APIPostAsyncDLOcrResponse();
			expectedRepsonse.setStatus(csvResponse.getStatus());
			expectedRepsonse.setCompletedAt(csvResponse.getCompletedAt());
			expectedRepsonse.setCreatedAt(csvResponse.getCreatedAt());
			expectedRepsonse.setGroupId(csvResponse.getGroupId());
			expectedRepsonse.setRequestId(csvResponse.getRequestId());
			expectedRepsonse.setTat(Float.parseFloat(csvResponse.getTat()));
			DLOcrAsyncResult result = new DLOcrAsyncResult();
			result.setAddress(csvResponse.getAddress());
			result.setStreetAddress(csvResponse.getStreetAddress());
			result.setNameOnCard(csvResponse.getNameOnCard());
			result.setDlNumber(csvResponse.getDlNumber());
			result.setDateOfBirth(csvResponse.getDateOfBirth());
			result.setState(csvResponse.getState());
			result.setHouseNumber(csvResponse.getHouseNumber());
			result.setFathersName(csvResponse.getFathersName());
			result.setPincode(csvResponse.getPincode());
			result.setIsScanned(csvResponse.getIsScanned());
			ArrayList<String> type = new ArrayList();
			if (null != csvResponse.getType0())
				type.add(csvResponse.getType0());
			if (null != csvResponse.getType1())
				type.add(csvResponse.getType1());
			if (null != csvResponse.getType2())
				type.add(csvResponse.getType2());
			if (null != type)
				result.setType(type);
			Validity validity = new Validity();
			validity.setNt(csvResponse.getValidityNT());
			if (null != validity)
				result.setValidity(validity);
			IssueDates issueDates = new IssueDates();
			issueDates.setLmv(csvResponse.getIssueDatesLMV());
			issueDates.setLmvtr(csvResponse.getIssueDatesLMVTR());
			issueDates.setWtr(csvResponse.getIssueDates3WTR());
			issueDates.setMcwg(csvResponse.getIssueDatesMCWG());
			issueDates.setPsvbus(csvResponse.getIssueDatesPSVBUS());
			issueDates.setTrans(csvResponse.getIssueDatesTRANS());
			if (null != issueDates)
				result.setIssueDates(issueDates);
			result.setRawText(csvResponse.getRawText());
			expectedRepsonse.setOcrOutput(result);
			expectedResponses.add(expectedRepsonse);
		}
		return expectedResponses.toArray(new APIPostAsyncDLOcrResponse[0]);
	}

}
