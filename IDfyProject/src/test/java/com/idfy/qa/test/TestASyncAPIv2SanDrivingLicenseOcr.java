package com.idfy.qa.test;

import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import org.testng.Assert;
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
import com.idfy.qa.pojo.APIPostAsyncAadharOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncDLOcrResponse;
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
import io.qameta.allure.Attachment;

public class TestASyncAPIv2SanDrivingLicenseOcr {

	SoftAssert softAssert = new SoftAssert();
	private AsyncCalls asyncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;
	APIPostAsyncDLOcrResponse[] response;
	APIPostAsyncResponse postResponse;

	Set<String> exList = new LinkedHashSet<String>();
	
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
		exList.clear();
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
	
	 	
	@Test(description = "Test Aadhaar OCR Sanity", dataProvider = "voter-test", priority=1)
	public void testAsyncAadharOcr(APIPostAsyncRequests request, APIPostAsyncVoterOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("Aadhaar Image", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		postResponse = asyncCalls.dlPOSTOcr(request);
		Thread.sleep(9000);
		response = asyncCalls.dlGETocr(postResponse.getRequestId());
		Thread.sleep(5000);
		
		
		try {
		if(postResponse.getRequestId()!=null) {
			exList.add("request_id");
		}}catch(NullPointerException e) {
			
		}
		try {
			if(postResponse.getStatus()!=null) {
				exList.add("status");	
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getCompletedAt()!=null) {
				exList.add("completed_at");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getCreatedAt()!=null) {
				exList.add("created_at");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getTaskId()!=null) {
				exList.add("task_id");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getGroupId()!=null) {
				exList.add("group_id");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getTat()!=null) {
				exList.add("tat");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput()!=null) {
				exList.add("ocr_output");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getDlNumber()!=null) {
				exList.add("dl_number");
			}}catch(NullPointerException e) {
				
		}
		
		try {
			if(response[0].getOcrOutput().getNameOnCard()!=null) {
				exList.add("name_on_card");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getDateOfBirth()!=null) {
				exList.add("date_of_birth");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getDateOfIssue()!=null) {
				exList.add("date_of_issue");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getFathersName()!=null) {
				exList.add("fathers_name");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getAddress()!=null) {
				exList.add("address");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getHouseNumber()!=null) {
				exList.add("house_number");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getStreetAddress()!=null) {
				exList.add("street_address");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getState()!=null) {
				exList.add("state");
			}}catch(NullPointerException e) {
				
		}
		
		try {
			if(response[0].getOcrOutput().getPincode()!=null) {
				exList.add("pincode");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getRawText()!=null) {
				exList.add("raw_text");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getIsScanned()!=null) {
				exList.add("is_scanned");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getMessage()!=null) {
				exList.add("message");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getError()!=null) {
				exList.add("error");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getError().getCode()!=null) {
				exList.add("code");
			}}catch(NullPointerException e) {
				
		}
		
		try {
			if(response[0].getError().getDescription()!=null) {
				exList.add("description");
			}}catch(NullPointerException e) {
				
		}
		
	}
	@Test(description = "Test Voter Attributes Sanity",priority=2)
	public void testVoterAttributes()
			throws IOException, InterruptedException{
		System.out.println("ArrayList without duplicates: "+ exList); 
		
	}
	
	@Attachment
    public String makeAttachTrue(String str) {
        return  ("V2::Async::Staging:: voter_ocr:: "+str+" : Attribute is Present");
    }
	
    public String makeAttachFalse(String str) {
        return  ("V2::Async::Staging:: voter_ocr:: "+str+" : Attribute is Missed in Response");
    }
	
	@Test(description = "Assertion for request_id",priority=3)
	public void testVoterOcrAssertRequestid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("request_id"),makeAttachFalse("request_id"));
			makeAttachTrue("request_id");
	}
	@Test(description = "Assertion for Status",priority=4)
	public void testVoterOcrAssertStatus()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("status"),makeAttachFalse("status"));
			makeAttachTrue("status");
	}
	@Test(description = "Assertion for task_id",priority=5)
	public void testVoterOcrtask_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("task_id"),makeAttachFalse("task_id"));
			makeAttachTrue("task_id");
	}
	@Test(description = "Assertion for group_id",priority=6)
	public void testVoterOcrGroup_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("group_id"),makeAttachFalse("group_id"));
			makeAttachTrue("group_id");
	}
	@Test(description = "Assertion for completed_at",priority=7)
	public void testVoterOcrOutputid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("completed_at"),makeAttachFalse("completed_at"));
			makeAttachTrue("completed_at");
	}	 
	@Test(description = "Assertion for created_at",priority=8)
	public void testVoterOcrCreatedAt()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("created_at"),makeAttachFalse("created_at"));
			makeAttachTrue("created_at");
	}
	@Test(description = "Assertion for tat",priority=9)
	public void testVoterOcrTat()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("tat"),makeAttachFalse("tat"));
			makeAttachTrue("tat");
	}
	@Test(description = "Assertion for ocr_output",priority=10)
	public void testVoterOcrOutput()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("ocr_output"),makeAttachFalse("ocr_output"));
			makeAttachTrue("ocr_output");
	}
	@Test(description = "Assertion for aadhaar_number",priority=11)
	public void testVoterOcrDlNumber()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("dl_number"),makeAttachFalse("dl_number"));
			makeAttachTrue("dl_number");
	}
	@Test(description = "Assertion for name_on_card",priority=12)
	public void testVoterOcrNameOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("name_on_card"),makeAttachFalse("name_on_card"));
			makeAttachTrue("name_on_card");
	}
	@Test(description = "Assertion for date_of_birth",priority=13)
	public void testVoterOcrDateOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("date_of_birth"),makeAttachFalse("date_of_birth"));
			makeAttachTrue("date_of_birth");
	}
	@Test(description = "Assertion for date_of_issue",priority=14)
	public void testVoterOcrAge()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("date_of_issue"),makeAttachFalse("date_of_issue"));
			makeAttachTrue("date_of_issue");
	}
	@Test(description = "Assertion for fathers_name",priority=15)
	public void testVoterOcrGender()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("fathers_name"),makeAttachFalse("fathers_name"));
			makeAttachTrue("fathers_name");
	}
	
	@Test(description = "Assertion for address",priority=17)
	public void testVoterOcrAddress()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("address"),makeAttachFalse("address"));
			makeAttachTrue("address");
	}
	@Test(description = "Assertion for house_number",priority=18)
	public void testVoterOcrHouseNumber()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("house_number"),makeAttachFalse("house_number"));
			makeAttachTrue("house_number");
	}
	@Test(description = "Assertion for street_address",priority=19)
	public void testVoterOcrStreetAddress()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("street_address"),makeAttachFalse("street_address"));
			makeAttachTrue("street_address");
	}
	@Test(description = "Assertion for district",priority=20)
	public void testVoterOcrDistrictName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("district"),makeAttachFalse("district"));
			makeAttachTrue("district");
	}
	@Test(description = "Assertion for state_name",priority=21)
	public void testVoterOcrStateName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("state"),makeAttachFalse("state"));
			makeAttachTrue("state");
	}
	@Test(description = "Assertion for pincode",priority=22)
	public void testVoterOcrPincode()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("pincode"),makeAttachFalse("pincode"));
			makeAttachTrue("pincode");
	}
	@Test(description = "Assertion for raw_text",priority=23)
	public void testVoterOcrRawText()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("raw_text"),makeAttachFalse("raw_text"));
			makeAttachTrue("raw_text");
	}
	@Test(description = "Assertion for message",priority=24)
	public void testVoterOcrMessage()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("message"),makeAttachFalse("message"));
			makeAttachTrue("message");
	}
	@Test(description = "Assertion for error",priority=25)
	public void testVoterOcrError()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("error"),makeAttachFalse("error"));
			makeAttachTrue("error");
			
	}
	@Test(description = "Assertion for raw_text",priority=26)
	public void testVoterOcrIsScanned()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("is_scanned"),makeAttachFalse("is_scanned"));
			makeAttachTrue("is_scanned");
	}
	@Test(description = "Assertion for code",priority=27)
	public void testVoterOcrCode()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("code"),makeAttachFalse("code"));
			makeAttachTrue("code");
	}
	@Test(description = "Assertion for description",priority=28)
	public void testVoterOcrDescription()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("description"),makeAttachFalse("description"));
			makeAttachTrue("description");
	}
}
		
	

		
	
	
