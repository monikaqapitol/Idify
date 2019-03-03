package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostRequest;
import com.idfy.qa.pojo.CSVRequest;
import com.idfy.qa.pojo.RequestData;

import lombok.Data;

@Data
public class RequestCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public RequestCSVDataProviderImpl(String filename) {
		this.fileName = filename;
	}

	@Override
	public CSVRequest[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<CSVRequest> map = mapper.readerFor(CSVRequest.class).with(schema).readValues(csvFile);
		ArrayList<CSVRequest> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new CSVRequest[0]);
	}

	@Override
	public APIPostRequest[] parseAllRows() throws IOException {
		CSVRequest[] csvrequests = this.readAllRows();
		ArrayList<APIPostRequest> requests = new ArrayList();
		for (CSVRequest csvrequest : csvrequests) {
			APIPostRequest request = new APIPostRequest();
			request.setType(csvrequest.getType());
			request.setGroupId(csvrequest.getGroupId());
			request.setTaskId(csvrequest.getTaskId());
			RequestData data = new RequestData();
			if(csvrequest.getType().equalsIgnoreCase("face_validation")) {
				data.setDocUrl(csvrequest.getData());
			}else {
				if (csvrequest.getData().contains(",")) {
					data.setDocUrls(csvrequest.getData().split(","));
				} else {
					data.setDocUrls(new String[] { csvrequest.getData() });
				}
			}
			if (null != csvrequest.getConsent())
				data.setConsent(csvrequest.getConsent());
			request.setData(data);
			requests.add(request);
		}

		return requests.toArray(new APIPostRequest[0]);
	}

}
