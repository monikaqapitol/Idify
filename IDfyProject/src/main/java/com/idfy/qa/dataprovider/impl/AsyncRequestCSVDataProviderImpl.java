package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostAsyncRequest;
import com.idfy.qa.pojo.APIPostAsyncRequests;
import com.idfy.qa.pojo.AsyncRequestData;
import com.idfy.qa.pojo.CSVRequest;

public class AsyncRequestCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public AsyncRequestCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
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
	public APIPostAsyncRequests[] parseAllRows() throws IOException {
		CSVRequest[] csvrequests = this.readAllRows();
		ArrayList<APIPostAsyncRequests> requests = new ArrayList();
		for (CSVRequest csvrequest : csvrequests) {
			APIPostAsyncRequests asyncResquests = new APIPostAsyncRequests();
			APIPostAsyncRequest request = new APIPostAsyncRequest();
			request.setType(csvrequest.getType());
			request.setGroupId(csvrequest.getGroupId());
			request.setTaskId(csvrequest.getTaskId());
			AsyncRequestData data = new AsyncRequestData();
			if (csvrequest.getType().equalsIgnoreCase("face_compare") && csvrequest.getData().contains(",")) {
				data.setDocUrl(csvrequest.getData().split(","));
			} else {
				data.setDocUrl(new String[] { csvrequest.getData() });
			}
			if (null != csvrequest.getConsent())
				data.setAadharConsent(csvrequest.getConsent());
			request.setData(data);
			asyncResquests.setTasks(new APIPostAsyncRequest[] {request});
			requests.add(asyncResquests);
		}

		return requests.toArray(new APIPostAsyncRequests[0]);
	}

}
