package com.idfy.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.idfy.qa.api.AsyncCalls;
import com.idfy.qa.api.SyncCalls;
import com.idfy.qa.dataprovider.CSVDataProvider;
import com.idfy.qa.dataprovider.impl.AadharMaskingResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AadharResponseCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncRequestCSVDataProviderImpl;
import com.idfy.qa.dataprovider.impl.AsyncVoterResponseCSVDataProviderImpl;
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
import com.idfy.qa.pojo.APIPostAsyncRequests;
import com.idfy.qa.pojo.APIPostAsyncResponse;
import com.idfy.qa.pojo.APIPostAsyncVoterOcrResponse;
import com.idfy.qa.pojo.APIPostChequeResponse;
import com.idfy.qa.pojo.APIPostDLResponse;
import com.idfy.qa.pojo.APIPostFaceCompareResponse;
import com.idfy.qa.pojo.APIPostFaceValidationResponse;
import com.idfy.qa.pojo.APIPostPanResponse;
import com.idfy.qa.pojo.APIPostPassportResponse;
import com.idfy.qa.pojo.APIPostRequest;
import com.idfy.qa.pojo.APIPostVoterResponse;
import com.idfy.qa.pojo.VoterOcrAsyncResult;
import com.idfy.qa.pojo.VoterResult;

import io.qameta.allure.Allure;
import io.qameta.allure.jaxrs.AllureJaxRs;

public class TestASyncAPIv2San2 {

	SoftAssert softAssert = new SoftAssert();
	private AsyncCalls asyncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;
	APIPostAsyncVoterOcrResponse[] response;
	APIPostAsyncResponse postResponse;

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
		client = client.header("account_id", "30ebd0cea1f9/84a55ab9-9b3a-4140-ae86-f0782f06ff10");
	}

	@DataProvider(name = "voter-test")
	public Object[][] createVoterData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src/test/resources/voter/voter_data1.csv");
		responseCSVProcess = new AsyncVoterResponseCSVDataProviderImpl(
				"src/test/resources/voter/voter_async_response1.csv");
		APIPostAsyncRequests[] requests = (APIPostAsyncRequests[]) requestCSVProcess.parseAllRows();
		APIPostAsyncVoterOcrResponse[] expectedResponses = (APIPostAsyncVoterOcrResponse[]) responseCSVProcess
				.parseAllRows();
		Object[][] obj = new Object[requests.length][];
		for (int i = 0, j = 0; i < requests.length && j < expectedResponses.length; i++, j++) {
			obj[i] = new Object[] { requests[i], expectedResponses[j] };
		}
		return obj;
	}

	@Test(description = "Test Voter OCR Sanity", dataProvider = "voter-test", priority =1)
	public void testVoterOcr(APIPostAsyncRequests request, APIPostAsyncVoterOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		postResponse = asyncCalls.voterPOSTOcr(request);
		Thread.sleep(10000);
		response = asyncCalls.voterGETocr(postResponse.getRequestId());
		Thread.sleep(3000);
	}
		
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertRequestId()
				throws IOException, InterruptedException {	
				
		assertNotNull(postResponse.getRequestId(),"ASync:: voter_ocr::POST request_id Attribute Missed in the Response");
	
	}
	
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertStatus()
				throws IOException, InterruptedException {	
				
		assertNotNull(postResponse.getStatus(),"ASync:: voter_ocr::POST status Attribute Missed in the Response");
	
	}

	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertCreatedAt()
				throws IOException, InterruptedException {	
				
		assertNotNull(response[0].getCreatedAt(),"ASync:: voter_ocr::Get CreatedAt Attribute Missed in the Response");
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertTaskId()
				throws IOException, InterruptedException {	
				
		assertNotNull(response[0].getTaskId(),"ASync:: voter_ocr::Get TaskID Attribute Missed in the Response");
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertGroupId()
				throws IOException, InterruptedException {	
				
		assertNotNull(response[0].getGroupId(),"ASync:: voter_ocr::Get GroupID Attribute Missed in the Response");
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertTat()
				throws IOException, InterruptedException {	
				
		assertNotNull(response[0].getTat(),"ASync:: voter_ocr::Get Tat Attribute Missed in the Response");
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertError()
				throws IOException, InterruptedException {	
		if(response[0].getError()!=null) {		
		assertNotNull(response[0].getError(),"ASync:: voter_ocr::Get Error Attribute Missed in the Response");
		}else {}
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertVoterNumber()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getVoterNumber()!=null) {		
		assertNotNull(response[0].getOcrOutput().getVoterNumber(),"ASync::voter_ocr VoterNumber Attribute Missed in the Response");
		}else {}
	
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertNameOnCard()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getNameOnCard()!=null) {		
		assertNotNull(response[0].getOcrOutput().getNameOnCard(),"ASync::voter_ocr NameOnCard Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertAddress()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getAddress()!=null) {		
		assertNotNull(response[0].getOcrOutput().getAddress(),"ASync::voter_ocr Address Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertAge()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getAge()!=null) {		
		assertNotNull(response[0].getOcrOutput().getAge(),"ASync::voter_ocr Age Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertAgeAsOnYear()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getAgeAsOnYear()!=null) {		
		assertNotNull(response[0].getOcrOutput().getAgeAsOnYear(),"ASync::voter_ocr AgeAsOnYear Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertDateOfBirth()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getDateOfBirth()!=null) {		
		assertNotNull(response[0].getOcrOutput().getDateOfBirth(),"Sync::voter_ocr DateOfBirth Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertDistrictName()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getDistrictName()!=null) {		
		assertNotNull(response[0].getOcrOutput().getDistrictName(),"Sync::voter_ocr District Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertFathersName()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getFathersName()!=null) {		
		assertNotNull(response[0].getOcrOutput().getFathersName(),"Sync::voter_ocr FathersName Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertGender()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getGender()!=null) {		
		assertNotNull(response[0].getOcrOutput().getGender(),"Sync::voter_ocr Gender Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertHouseNumber()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getHouseNumber()!=null) {		
		assertNotNull(response[0].getOcrOutput().getHouseNumber(),"Sync::voter_ocr HouseNumber Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertPincode()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getPincode()!=null) {		
		assertNotNull(response[0].getOcrOutput().getPincode(),"Sync::voter_ocr Pincode Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertState()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getState()!=null) {		
		assertNotNull(response[0].getOcrOutput().getState(),"Sync::voter_ocr State Attribute Missed in the Response");
		}else {}
	}
	@Test(dependsOnMethods = {"testVoterOcr"}, priority =2)
	public void testAssertStreetAddress()
				throws IOException, InterruptedException {	
		if(response[0].getOcrOutput().getStreetAddress()!=null) {		
		assertNotNull(response[0].getOcrOutput().getStreetAddress(),"Sync::voter_ocr StreetAddress Attribute Missed in the Response");
		}else {}
	}
	
	
}
