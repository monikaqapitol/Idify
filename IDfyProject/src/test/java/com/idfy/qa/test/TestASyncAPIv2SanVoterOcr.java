package com.idfy.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.web.bind.annotation.ResponseBody;
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

public class TestASyncAPIv2SanVoterOcr {

	SoftAssert softAssert = new SoftAssert();
	private AsyncCalls asyncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;
	APIPostAsyncVoterOcrResponse[] response;
	APIPostAsyncResponse postResponse;

	Set<String> exList = new LinkedHashSet<String>();
	ArrayList<String> exNoDupList = new ArrayList<String>();
	Set<String> acList = new LinkedHashSet<String>(Arrays.asList("request_id","status","task_id","group_id","output_id"));
	
	
	Iterator<String> exI=exList.iterator();
	
	
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
	
	 	
	@Test(description = "Test Voter OCR Sanity", dataProvider = "voter-test", priority=1)
	public void testVoterOcr1(APIPostAsyncRequests request, APIPostAsyncVoterOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		postResponse = asyncCalls.voterPOSTOcr(request);
		Thread.sleep(9000);
		response = asyncCalls.voterGETocr(postResponse.getRequestId());
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
			if(response[0].getOcrOutput().getVoterNumber()!=null) {
				exList.add("voter_number");
			}}catch(NullPointerException e) {
				
		}
		
		try {
			if(response[0].getOcrOutput().getNameOnCard()!=null) {
				exList.add("name_on_card");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getDateOnCard()!=null) {
				exList.add("date_on_card");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getAge()!=null) {
				exList.add("age");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getGender()!=null) {
				exList.add("gender");
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
			if(response[0].getOcrOutput().getDistrictName()!=null) {
				exList.add("district_name");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getOcrOutput().getState()!=null) {
				exList.add("state_name");
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
	@Test(description = "Assertion for Status",priority=3)
	public void testVoterOcrAssertStatus()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("status"),makeAttachFalse("status"));
			makeAttachTrue("status");
	}
	@Test(description = "Assertion for task_id",priority=3)
	public void testVoterOcrtask_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("task_id"),makeAttachFalse("task_id"));
			makeAttachTrue("task_id");
	}
	@Test(description = "Assertion for group_id",priority=3)
	public void testVoterOcrGroup_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("group_id"),makeAttachFalse("group_id"));
			makeAttachTrue("group_id");
	}
	@Test(description = "Assertion for completed_at",priority=3)
	public void testVoterOcrOutputid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("completed_at"),makeAttachFalse("completed_at"));
			makeAttachTrue("completed_at");
	}	 
	@Test(description = "Assertion for created_at",priority=3)
	public void testVoterOcrCreatedAt()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("created_at"),makeAttachFalse("created_at"));
			makeAttachTrue("created_at");
	}
	@Test(description = "Assertion for tat",priority=3)
	public void testVoterOcrTat()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("tat"),makeAttachFalse("tat"));
			makeAttachTrue("tat");
	}
	@Test(description = "Assertion for ocr_output",priority=3)
	public void testVoterOcrOutput()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("ocr_output"),makeAttachFalse("ocr_output"));
			makeAttachTrue("ocr_output");
	}
	@Test(description = "Assertion for voter_number",priority=3)
	public void testVoterOcrVoterNumber()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("voter_number"),makeAttachFalse("voter_number"));
			makeAttachTrue("voter_number");
	}
	@Test(description = "Assertion for name_on_card",priority=3)
	public void testVoterOcrNameOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("name_on_card"),makeAttachFalse("name_on_card"));
			makeAttachTrue("name_on_card");
	}
	@Test(description = "Assertion for date_on_card",priority=3)
	public void testVoterOcrDateOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("date_on_card"),makeAttachFalse("date_on_card"));
			makeAttachTrue("date_on_card");
	}
	@Test(description = "Assertion for age",priority=3)
	public void testVoterOcrAge()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("age"),makeAttachFalse("age"));
			makeAttachTrue("age");
	}
	@Test(description = "Assertion for gender",priority=3)
	public void testVoterOcrGender()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("gender"),makeAttachFalse("gender"));
			makeAttachTrue("gender");
	}
	@Test(description = "Assertion for fathers_name",priority=3)
	public void testVoterOcrFathersName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("fathers_name"),makeAttachFalse("fathers_name"));
			makeAttachTrue("fathers_name");
	}
	@Test(description = "Assertion for address",priority=3)
	public void testVoterOcrAddress()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("address"),makeAttachFalse("address"));
			makeAttachTrue("address");
	}
	@Test(description = "Assertion for house_number",priority=3)
	public void testVoterOcrHouseNumber()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("house_number"),makeAttachFalse("house_number"));
			makeAttachTrue("house_number");
	}
	@Test(description = "Assertion for street_address",priority=3)
	public void testVoterOcrStreetAddress()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("street_address"),makeAttachFalse("street_address"));
			makeAttachTrue("street_address");
	}
	@Test(description = "Assertion for district_name",priority=3)
	public void testVoterOcrDistrictName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("district_name"),makeAttachFalse("district_name"));
			makeAttachTrue("district_name");
	}
	@Test(description = "Assertion for state_name",priority=3)
	public void testVoterOcrStateName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("state_name"),makeAttachFalse("state_name"));
			makeAttachTrue("state_name");
	}
	@Test(description = "Assertion for pincode",priority=3)
	public void testVoterOcrPincode()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("pincode"),makeAttachFalse("pincode"));
			makeAttachTrue("pincode");
	}
	@Test(description = "Assertion for raw_text",priority=3)
	public void testVoterOcrRawText()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("raw_text"),makeAttachFalse("raw_text"));
			makeAttachTrue("raw_text");
	}
	@Test(description = "Assertion for raw_text",priority=3)
	public void testVoterOcrMessage()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("message"),makeAttachFalse("message"));
			makeAttachTrue("message");
	}
	@Test(description = "Assertion for raw_text",priority=3)
	public void testVoterOcrError()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("error"),makeAttachFalse("error"));
			makeAttachTrue("error");
	}
}
		
	

		
	
	
