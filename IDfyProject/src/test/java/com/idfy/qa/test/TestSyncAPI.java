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

import io.qameta.allure.Allure;
import io.qameta.allure.jaxrs.AllureJaxRs;

public class TestSyncAPI {

	private SyncCalls syncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;

	@Parameters({ "URL", "apiKey" })
	@BeforeClass
	public void config(String URL, String apiKey) {
		List<Object> providers = new ArrayList();
		providers.add(new JacksonJsonProvider());
		providers.add(new AllureJaxRs());
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
		Allure.addAttachment("VoterImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostVoterResponse response = syncCalls.voterOcr(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());

	}

	@DataProvider(name = "pan-test")
	public Object[][] createPanData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/pan/pan_data.csv");
		responseCSVProcess = new PanResponseCSVDataProviderImpl("src/test/resources/pan/pan_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostPanResponse[] expectedResponses = (APIPostPanResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Pan OCR", dataProvider = "pan-test")
	public void testPanOcr(APIPostRequest request, APIPostPanResponse expectedResponse) throws IOException {
		Allure.addAttachment("PanImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostPanResponse response = syncCalls.panOcr(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());

	}

	@DataProvider(name = "aadhar-test")
	public Object[][] createAadharData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/aadhar/aadhar_data.csv");
		responseCSVProcess = new AadharResponseCSVDataProviderImpl("src/test/resources/aadhar/aadhar_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostAadharResponse[] expectedResponses = (APIPostAadharResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Aadhar OCR", dataProvider = "aadhar-test")
	public void testAadharOcr(APIPostRequest request, APIPostAadharResponse expectedResponse) throws IOException {
		Allure.addAttachment("AadharImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostAadharResponse response = syncCalls.aadharOcr(request);
		assertEquals(expectedResponse.getResult().getOcrOutput().getIdNumber(),
				response.getResult().getOcrOutput().getIdNumber());

	}

	@DataProvider(name = "aadhar-mask-test")
	public Object[][] createAadharMaskData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/aadhar/aadhar_masking_data.csv");
		responseCSVProcess = new AadharMaskingResponseCSVDataProviderImpl(
				"src/test/resources/aadhar/aadhar_masking_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostAadharMaskingResponse[] expectedResponses = (APIPostAadharMaskingResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Aadhar Masking", dataProvider = "aadhar-mask-test")
	public void testAdharMasking(APIPostRequest request, APIPostAadharMaskingResponse expectedResponse)
			throws IOException {
		Allure.addAttachment("AadharMaskingImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostAadharMaskingResponse response = syncCalls.aadharMasking(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());

	}

	@DataProvider(name = "dl-test")
	public Object[][] createDLData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/dl/dl_data.csv");
		responseCSVProcess = new DLResponseCSVDataProviderImpl("src/test/resources/dl/dl_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostDLResponse[] expectedResponses = (APIPostDLResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test DL OCR", dataProvider = "dl-test")
	public void testDLOcr(APIPostRequest request, APIPostDLResponse expectedResponse) throws IOException {
		Allure.addAttachment("DLImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostDLResponse response = syncCalls.dlOcr(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());
	}

	@DataProvider(name = "cheque-test")
	public Object[][] createChequeData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/cheque/cheque_data.csv");
		responseCSVProcess = new ChequeResponseCSVDataProviderImpl("src/test/resources/cheque/cheque_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostChequeResponse[] expectedResponses = (APIPostChequeResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Cheque OCR", dataProvider = "cheque-test")
	public void testChequeOcr(APIPostRequest request, APIPostChequeResponse expectedResponse) throws IOException {
		Allure.addAttachment("ChequeImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostChequeResponse response = syncCalls.chequeOcr(request);
		assertEquals(expectedResponse.getResult().getAccountNo(), response.getResult().getAccountNo());
	}

	@DataProvider(name = "face-validation-test")
	public Object[][] createFaceValidationData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/face/face_validation_data.csv");
		responseCSVProcess = new FaceValidationCSVDataProviderImpl(
				"src/test/resources/face/face_validation_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostFaceValidationResponse[] expectedResponses = (APIPostFaceValidationResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Face Validation", dataProvider = "face-validation-test")
	public void testFaceValidation(APIPostRequest request, APIPostFaceValidationResponse expectedResponse)
			throws IOException {
		Allure.addAttachment("FaceImage", new URL(request.getData().getDocUrl()).openStream());
		APIPostFaceValidationResponse response = syncCalls.faceValidation(request);
		assertEquals(expectedResponse.getResult(), response.getResult());
	}

	@DataProvider(name = "face-compare-test")
	public Object[][] createFaceCompareData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/face/face_compare_data.csv");
		responseCSVProcess = new FaceCompareResponseCSVDataProviderImpl(
				"src/test/resources/face/face_compare_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostFaceCompareResponse[] expectedResponses = (APIPostFaceCompareResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test FaceCompare", dataProvider = "face-compare-test")
	public void testFaceCompare(APIPostRequest request, APIPostFaceCompareResponse expectedResponse)
			throws IOException {
		Allure.addAttachment("FaceImage1", new URL(request.getData().getDocUrls()[0]).openStream());
		Allure.addAttachment("FaceImage2", new URL(request.getData().getDocUrls()[1]).openStream());
		APIPostFaceCompareResponse response = syncCalls.faceCompare(request);
		assertEquals(expectedResponse.getResult(), response.getResult());
	}

	@DataProvider(name = "passport-test")
	public Object[][] createPassportData() throws IOException {
		requestCSVProcess = new RequestCSVDataProviderImpl("src/test/resources/passport/passport_data.csv");
		responseCSVProcess = new PassportResponseCSVDataProviderImpl(
				"src/test/resources/passport/passport_response.csv");
		APIPostRequest[] requests = (APIPostRequest[]) requestCSVProcess.parseAllRows();
		APIPostPassportResponse[] expectedResponses = (APIPostPassportResponse[]) responseCSVProcess.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "test Passport OCR", dataProvider = "passport-test")
	public void testPassportOcr(APIPostRequest request, APIPostPassportResponse expectedResponse) throws IOException {
		Allure.addAttachment("PassportImage", new URL(request.getData().getDocUrls()[0]).openStream());
		APIPostPassportResponse response = syncCalls.passportOcr(request);
		assertEquals(expectedResponse.getStatus(), response.getStatus());
	}

}
