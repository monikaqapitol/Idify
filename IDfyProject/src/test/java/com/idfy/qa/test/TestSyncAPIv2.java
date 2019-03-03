package com.idfy.qa.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.idfy.qa.api.SyncCalls;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.dataprovider.impl.AadharMaskingResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AadharResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.ChequeResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.DLResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.FaceCompareResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.FaceValidationCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.PanResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.PassportResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.RequestCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.VoterResponseCSVDataProviderImpl;
import com.idfy.qa.pojo.APIPostAadharMaskingResponse;
import com.idfy.qa.pojo.APIPostAadharResponse;
import com.idfy.qa.pojo.APIPostChequeResponse;
import com.idfy.qa.pojo.APIPostDLResponse;
import com.idfy.qa.pojo.APIPostFaceCompareResponse;
import com.idfy.qa.pojo.APIPostFaceValidationResponse;
import com.idfy.qa.pojo.APIPostPanResponse;
import com.idfy.qa.pojo.APIPostPassportResponse;
import com.idfy.qa.pojo.APIPostRequest;
import com.idfy.qa.pojo.APIPostVoterResponse;

public class TestSyncAPIv2 {

	private SyncCalls syncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;

	@Parameters({ "URL", "apiKey" })
	@BeforeClass
	public void config(String URL, String apiKey) {
		List<Object> providers = new ArrayList();
		providers.add(new JacksonJsonProvider());

		syncCalls = JAXRSClientFactory.create(URL, SyncCalls.class, providers);
		Client client = WebClient.client(syncCalls);
		client = client.header("api_key", apiKey);
		client = client.header("Content-Type", "application/json");
		client = client.header("account_id", "");
	}

	@DataProvider(name = "voter-test")
	public Object[][] createVoterData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/voter/voter_data.csv");
		responseCSVProcess = new VoterResponseCSVDataProviderImpl("src/test/resources/voter/voter_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostVoterResponse[] expectedResponses = (APIPostVoterResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Voter OCR", dataProvider = "voter-test")
	public void testVoterOcr(APIPostRequest request, APIPostVoterResponse expectedResponse) throws IOException {
		APIPostVoterResponse response = syncCalls.voterOcr(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());
		
	}

	
}
