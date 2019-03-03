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
import com.idfy.qa.pojo.APIPostAsyncPanOcrResponse;
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

public class Pantest {

	SoftAssert softAssert = new SoftAssert();
	private AsyncCalls asyncCalls;
	private CSVDataProvider requestCSVProcess;
	private CSVDataProvider responseCSVProcess;
	APIPostAsyncPanOcrResponse[] response;
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
	}

	@DataProvider(name = "pan-test1")
	public Object[][] createVoterData() throws IOException {
		requestCSVProcess = new AsyncRequestCSVDataProviderImpl("src\\test\\resources\\pan\\pan_data.csv");
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
	
	 	
	@Test(description = "Test Pan OCR Sanity", dataProvider = "pan-test1", priority=1)
	public void testPanOcr1(APIPostAsyncRequests request, APIPostAsyncVoterOcrResponse expectedResponse)
			throws IOException, InterruptedException {
		Allure.addAttachment("VoterImage", new URL(request.getTasks()[0].getData().getDocUrl()[0]).openStream());
		postResponse = asyncCalls.panPOSTOcr(request);
		Thread.sleep(9000);
		response = asyncCalls.panGETocr(postResponse.getRequestId());
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
			if(response[0].getOcrOutput().getPanNumber()!=null) {
				exList.add("pan_number");
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
			if(response[0].getOcrOutput().isScanned()==(true||false)) {
				exList.add("is_scanned");
			}}catch(NullPointerException e) {
				
			}
		try {
			if(response[0].getOcrOutput().isMinor()==(true||false)) {
				exList.add("minor");
			}}catch(NullPointerException e) {
				
			}
		
		
		try {
			if(response[0].getOcrOutput().getPanType()!=null) {
				exList.add("pan_type");
			}}catch(NullPointerException e) {
				
			}
		
		try {
			if(response[0].getOcrOutput().getFathersName()!=null) {
				exList.add("fathers_name");
			}}catch(NullPointerException e) {
				
		}
		
		
		try {
			if(response[0].getOcrOutput().getRawText()!=null) {
				exList.add("raw_text");
			}}catch(NullPointerException e) {
				
		}
	}
	@Test(description = "Test Pan Attributes Sanity",priority=2)
	public void testPanAttributes()
			throws IOException, InterruptedException{
		System.out.println("ArrayList without duplicates: "+ exList); 
		
	}
	
	@Attachment
    public String makeAttachTrue(String str) {
        return  ("V2::Async::Staging:: pan_ocr:: "+str+" : Attribute is Present");
    }
	
    public String makeAttachFalse(String str) {
        return  ("V2::Async::Staging:: pan_ocr:: "+str+" : Attribute is Missed in Response");
    }
	
	@Test(description = "Assertion for request_id",priority=3)
	public void testPanOcrAssertRequestid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("request_id"),makeAttachFalse("request_id"));
			makeAttachTrue("request_id");
	}
	@Test(description = "Assertion for Status",priority=3)
	public void testPanOcrAssertStatus()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("status"),makeAttachFalse("status"));
			makeAttachTrue("status");
	}
	@Test(description = "Assertion for task_id",priority=3)
	public void testPanOcrtask_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("task_id"),makeAttachFalse("task_id"));
			makeAttachTrue("task_id");
	}
	@Test(description = "Assertion for group_id",priority=3)
	public void testPanOcrGroup_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("group_id"),makeAttachFalse("group_id"));
			makeAttachTrue("group_id");
	}
	@Test(description = "Assertion for completed_at",priority=3)
	public void testPanOcrOutputid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("completed_at"),makeAttachFalse("completed_at"));
			makeAttachTrue("completed_at");
	}	 
	@Test(description = "Assertion for created_at",priority=3)
	public void testPanbOcrCreatedAt()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("created_at"),makeAttachFalse("created_at"));
			makeAttachTrue("created_at");
	}
	@Test(description = "Assertion for tat",priority=3)
	public void testPanOcrTat()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("tat"),makeAttachFalse("tat"));
			makeAttachTrue("tat");
	}
	@Test(description = "Assertion for ocr_output",priority=3)
	public void testPanOcrOutput()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("ocr_output"),makeAttachFalse("ocr_output"));
			makeAttachTrue("ocr_output");
	}
	@Test(description = "Assertion for pan_number",priority=3)
	public void testPanOcrPanNumber()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("pan_number"),makeAttachFalse("pan_number"));
			makeAttachTrue("pan_number");
	}
	@Test(description = "Assertion for name_on_card",priority=3)
	public void testPanOcrNameOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("name_on_card"),makeAttachFalse("name_on_card"));
			makeAttachTrue("name_on_card");
	}
	@Test(description = "Assertion for date_on_card",priority=3)
	public void testPanOcrDateOnCard()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("date_on_card"),makeAttachFalse("date_on_card"));
			makeAttachTrue("date_on_card");
	}
	@Test(description = "Assertion for age",priority=3)
	public void testPanOcrAge()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("age"),makeAttachFalse("age"));
			makeAttachTrue("age");
	}
	
	@Test(description = "Assertion for fathers_name",priority=3)
	public void testPanOcrFathersName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains(""),makeAttachFalse("fathers_name"));
			makeAttachTrue("fathers_name");
	}
	@Test(description = "Assertion for is_scanned",priority=3)
	public void testPanOcrIsScanned()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("is_scanned"),makeAttachFalse("is_scanned"));
			makeAttachTrue("is_scanned");
	}
	@Test(description = "Assertion for minor",priority=3)
	public void testPanOcrMinor()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("minor"),makeAttachFalse("minor"));
			makeAttachTrue("minor");
	}
	
	@Test(description = "Assertion for pan_type",priority=3)
	public void testPanOcrPanType()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("pan_type"),makeAttachFalse("pan_type"));
			makeAttachTrue("pan_type");
	}
	@Test(description = "Assertion for field_match_result",priority=3)
	public void testPanOcrFieldMatchResult()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("fields_match_result"),makeAttachFalse("fields_match_result"));
			makeAttachTrue("fields_match_result");
	}
	@Test(description = "Assertion for name",priority=3)
	public void testPanOcrName()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("name"),makeAttachFalse("name"));
			makeAttachTrue("name");
	}
	@Test(description = "Assertion for reviewRequired ",priority=3)
	public void testPanOcrReviewRequired()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("reviewRequired"),makeAttachFalse("reviewRequired"));
			makeAttachTrue("reviewRequired");
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







	







