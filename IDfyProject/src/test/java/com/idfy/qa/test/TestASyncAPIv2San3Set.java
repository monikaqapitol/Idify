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

public class TestASyncAPIv2San3Set {

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
			if(response[0].getRequestId()!=null) {
				exList.add("request_id");
			}}catch(NullPointerException e) {
				
		}
		try {
			if(response[0].getStatus()!=null) {
				exList.add("status");
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
	
	}
	@Test(description = "Test Voter Attributes Sanity",priority=2)
	public void testVoterAttributes()
			throws IOException, InterruptedException{
		System.out.println("ArrayList without duplicates: "+ exList); 
		
	}
	
	@Attachment
    public String makeAttach(String str) {
        return  (str+" : Attribute is Present");
    }
	
	@Test(description = "Assertion for request_id",priority=3)
	public void testVoterOcrAssertRequestid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("request_id"),"V2::Async::Staging:: voter_ocr:: request_id : Attribute is Missed in Response");
			makeAttach("V2::Async::Staging:: voter_ocr:: request_id");
	}
	@Test(description = "Assertion for Status",priority=3)
	public void testVoterOcrAssertStatus()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("status"),"V2::Async::Staging:: voter_ocr:: status : Attribute is Missed in Response");
			makeAttach("V2::Async::Staging:: voter_ocr:: status");
	}
	@Test(description = "Assertion for task_id",priority=3)
	public void testVoterOcrtask_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("task_id"),"V2::Async::Staging:: voter_ocr:: task_id : Attribute is Missed in Response");
			makeAttach("V2::Async::Staging:: voter_ocr:: task_id");
	}
	@Test(description = "Assertion for group_id",priority=3)
	public void testVoterOcrGroup_id()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("group_id"),"V2::Async::Staging:: voter_ocr:: group_id : Attribute is Missed in Response");
			makeAttach("V2::Async::Staging:: voter_ocr:: group_id");
	}
	@Test(description = "Assertion for output_id",priority=3)
	public void testVoterOcrOutputid()
			throws IOException, InterruptedException{
			Assert.assertTrue(exList.contains("output_id"),"V2::Async::Staging:: voter_ocr:: output_id : Attribute is Missed in Response");
			makeAttach("V2::Async::Staging:: voter_ocr:: output_id");
	}	  	
}
		
	

		
	
	
