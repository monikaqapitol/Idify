package com.idfy.qa.dataprovider.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.pojo.APIPostFaceCompareResponse;
import com.idfy.qa.pojo.Face;
import com.idfy.qa.pojo.FaceCompareCSVResponse;
import com.idfy.qa.pojo.FaceCompareResult;
import com.idfy.qa.pojo.FaceDetails;

public class FaceCompareResponseCSVDataProviderImpl implements CSVDataProvider {

	private String fileName;

	public FaceCompareResponseCSVDataProviderImpl(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public FaceCompareCSVResponse[] readAllRows() throws IOException {
		File csvFile = new File(fileName);
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<FaceCompareCSVResponse> map = mapper.readerFor(FaceCompareCSVResponse.class).with(schema)
				.readValues(csvFile);
		ArrayList<FaceCompareCSVResponse> requests = new ArrayList();
		while (map.hasNext()) {
			requests.add(map.nextValue());
		}
		return requests.toArray(new FaceCompareCSVResponse[0]);
	}

	@Override
	public APIPostFaceCompareResponse[] parseAllRows() throws IOException {
		FaceCompareCSVResponse[] csvResponses = this.readAllRows();
		ArrayList<APIPostFaceCompareResponse> expectedResponses = new ArrayList();
		for (FaceCompareCSVResponse csvResponse : csvResponses) {
			APIPostFaceCompareResponse expectedResponse = new APIPostFaceCompareResponse();
			expectedResponse.setStatus(csvResponse.getStatus());
			expectedResponse.setCompletedAt(csvResponse.getCompletedAt());
			expectedResponse.setCreatedAt(csvResponse.getCreatedAt());
			expectedResponse.setGroupId(csvResponse.getGroupId());
			expectedResponse.setRequestId(csvResponse.getRequestId());
			expectedResponse.setTaskId(csvResponse.getTaskId());
			expectedResponse.setTaskType(csvResponse.getTaskType());
			expectedResponse.setTat(Float.parseFloat(csvResponse.getTat()));
			FaceCompareResult result = new FaceCompareResult();
			result.setMatchStatus(csvResponse.getMatchStatus());
			result.setReviewRequired(csvResponse.getReviewRequired());
			result.setMatchScore(Float.parseFloat(csvResponse.getMatchScore()));
			FaceDetails faceDetails = new FaceDetails();
			Face face1 = new Face();
			Face face2 = new Face();
			face1.setQuality(csvResponse.getFace1Quality());
			face1.setStatus(csvResponse.getFace1Status());
			face2.setQuality(csvResponse.getFace2Quality());
			face2.setStatus(csvResponse.getFace2Status());
			faceDetails.setFace1(face1);
			faceDetails.setFace2(face2);
			result.setFaceDetails(faceDetails);
			expectedResponse.setResult(result);
			expectedResponses.add(expectedResponse);
		}
		return expectedResponses.toArray(new APIPostFaceCompareResponse[0]);
	}

}
