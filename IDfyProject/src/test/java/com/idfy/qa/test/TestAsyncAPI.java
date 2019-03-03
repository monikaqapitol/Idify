package com.idfy.qa.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
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
import com.idfy.qa.api.AsyncCalls;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.dataprovider.impl.AsyncAadharResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncChequeResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncDLResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncFaceValidationResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncPanResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncPassportResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncRequestCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncVoterResponseCSVDataProviderImpl;
import com.idfy.qa.pojo.APIPostAsyncAadharOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncChequeOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncDLOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncFaceValidationResponse;
import com.idfy.qa.pojo.APIPostAsyncPanOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncPassportOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncRequests;
import com.idfy.qa.pojo.APIPostAsyncResponse;
import com.idfy.qa.pojo.APIPostAsyncVoterOcrResponse;

import io.qameta.allure.Allure;
import io.qameta.allure.jaxrs.AllureJaxRs;

public class TestAsyncAPI {

	private AsyncCalls asyncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;

	@Parameters({ "URL", "apiKey" })
	@BeforeClass
	public void config(String URL, String apiKey) {
		List<Object> providers = new ArrayList();
		providers.add(new JacksonJsonProvider());
		providers.add(new AllureJaxRs());
		asyncCalls = JAXRSClientFactory.create(URL, AsyncCalls.class, providers);
		Client client = WebClient.client(asyncCalls);
		client = client.header("apikey", apiKey);
		client = client.header("Content-Type", "application/json");
		client = client.header("account_id", "");
	}

	@DataProvider(name = "voter-test")
	public Object[][] createVoterData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/voter/voter_data.csv");
		responseCSVProcess = new AsyncVoterResponseCSVDataProviderImpl(
				"src/test/resources/voter/voter_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncVoterOcrResponse[] expectedResponses = (APIPostAsyncVoterOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Voter OCR", dataProvider = "voter-test")
	public void testVoterOcr(APIPostAsyncRequests request, APIPostAsyncVoterOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.voterPOSTOcr(request);
		Thread.sleep(6000);
		APIPostAsyncVoterOcrResponse[] response = asyncCalls.voterGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "pan-test")
	public Object[][] createPanData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/pan/pan_data.csv");
		responseCSVProcess = new AsyncPanResponseCSVDataProviderImpl("src/test/resources/pan/pan_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncPanOcrResponse[] expectedResponses = (APIPostAsyncPanOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Pan OCR", dataProvider = "pan-test")
	public void testPanOcr(APIPostAsyncRequests request, APIPostAsyncPanOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.panPOSTOcr(request);
		Thread.sleep(6000);
		APIPostAsyncPanOcrResponse[] response = asyncCalls.panGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "aadhar-test")
	public Object[][] createAadharData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/aadhar/aadhar_data.csv");
		responseCSVProcess = new AsyncAadharResponseCSVDataProviderImpl(
				"src/test/resources/aadhar/aadhar_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncAadharOcrResponse[] expectedResponses = (APIPostAsyncAadharOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Aadhar OCR", dataProvider = "aadhar-test")
	public void testAadharOcr(APIPostAsyncRequests request, APIPostAsyncAadharOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.aadharPOSTOcr(request);
		Thread.sleep(6000);
		APIPostAsyncAadharOcrResponse[] response = asyncCalls.aadharGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "cheque-test")
	public Object[][] createChequeData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/cheque/cheque_data.csv");
		responseCSVProcess = new AsyncChequeResponseCSVDataProviderImpl(
				"src/test/resources/cheque/cheque_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncChequeOcrResponse[] expectedResponses = (APIPostAsyncChequeOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Cheque OCR", dataProvider = "cheque-test")
	public void testChequeOcr(APIPostAsyncRequests request, APIPostAsyncChequeOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.chequePOSTOcr(request);
		Thread.sleep(30000);
		APIPostAsyncChequeOcrResponse[] response = asyncCalls.chequeGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "dl-test")
	public Object[][] createDLData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/dl/dl_data.csv");
		responseCSVProcess = new AsyncDLResponseCSVDataProviderImpl("src/test/resources/dl/dl_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncDLOcrResponse[] expectedResponses = (APIPostAsyncDLOcrResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test DL OCR", dataProvider = "dl-test")
	public void testDLOcr(APIPostAsyncRequests request, APIPostAsyncDLOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.dlPOSTOcr(request);
		Thread.sleep(15000);
		APIPostAsyncDLOcrResponse[] response = asyncCalls.dlGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "passport-test")
	public Object[][] createPassportData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/passport/passport_data.csv");
		responseCSVProcess = new AsyncPassportResponseCSVDataProviderImpl(
				"src/test/resources/passport/passport_async_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncPassportOcrResponse[] expectedResponses = (APIPostAsyncPassportOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Passport OCR", dataProvider = "passport-test")
	public void testPassportOcr(APIPostAsyncRequests request, APIPostAsyncPassportOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.passportPOSTOcr(request);
		Thread.sleep(15000);
		APIPostAsyncPassportOcrResponse[] response = asyncCalls.passportGETocr(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

	@DataProvider(name = "face-validation-test")
	public Object[][] createFaceValidationData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/face/face_validation_data.csv");
		responseCSVProcess = new AsyncFaceValidationResponseCSVDataProviderImpl(
				"src/test/resources/face/face_validation_response.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncFaceValidationResponse[] expectedResponses = (APIPostAsyncFaceValidationResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Face Validaiton", dataProvider = "face-validation-test")
	public void testFaceValidation(APIPostAsyncRequests request, APIPostAsyncFaceValidationResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		APIPostAsyncResponse postResponse = asyncCalls.facePOSTvalidation(request);
		Thread.sleep(20000);
		APIPostAsyncFaceValidationResponse[] response = asyncCalls.faceGETvalidaiton(postResponse.getRequestId());
		assertEquals(expectedResponse.getStatus(), response[0].getStatus());

	}

}
