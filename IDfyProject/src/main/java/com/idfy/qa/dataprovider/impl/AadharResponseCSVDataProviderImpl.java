package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAadharResponse;
import com.idfy.qa.pojo.AadharCSVResponse;
import com.idfy.qa.pojo.AadharResult;
import com.idfy.qa.pojo.OCRResult;

public class AadharResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AadharResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public AadharCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<AadharCSVResponse> map = mapper.readerFor(AadharCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<AadharCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new AadharCSVResponse[0]);
	}

	@Override
	public APIPostAadharResponse[] parseAllRows() throws IOException {
		AadharCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostAadharResponse> expectedResponses = new ArrayList();
		for (AadharCSVResponse csvResponse : csvResponses) {
			APIPostAadharResponse expectedResponse = new APIPostAadharResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getGroupId());
			AadharResult result = new AadharResult();
			OCRResult ocrResult = new OCRResult();
			ocrResult.setState(csvResponse.getState());
			ocrResult.setFathersName(csvResponse.getFathersName());
			ocrResult.setAddress(csvResponse.getAddress());
			ocrResult.setStreetAddress(csvResponse.getStreetAddress());
			ocrResult.setPincode(csvResponse.getPincode());
			ocrResult.setIdNumber(csvResponse.getIdNumber());
			ocrResult.setNameOnCard(csvResponse.getNameOnCard());
			ocrResult.setDateOfBirth(csvResponse.getDateOfBirth());
			ocrResult.setHouseNumber(csvResponse.getHouseNumber());
			ocrResult.setIsScanned(csvResponse.getIsScanned());
			ocrResult.setYearOfBirth(csvResponse.getYearOfBirth());
			ocrResult.setHouseNumber(csvResponse.getHouseNumber());
			ocrResult.setGender(csvResponse.getGender());
			result.setOcrOutput(ocrResult);
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostAadharResponse[0]);
	}

}
