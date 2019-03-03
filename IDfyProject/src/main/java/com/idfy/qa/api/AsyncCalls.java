package com.idfy.qa.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.idfy.qa.pojo.APIPostAsyncAadharOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncChequeOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncDLOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncFaceValidationResponse;
import com.idfy.qa.pojo.APIPostAsyncPanOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncPassportOcrResponse;
import com.idfy.qa.pojo.APIPostAsyncRequests;
import com.idfy.qa.pojo.APIPostAsyncResponse;
import com.idfy.qa.pojo.APIPostAsyncVoterOcrResponse;

@Path("/v2/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AsyncCalls {

	@POST
	APIPostAsyncResponse voterPOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncVoterOcrResponse[] voterGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse panPOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncPanOcrResponse[] panGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse aadharPOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncAadharOcrResponse[] aadharGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse chequePOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncChequeOcrResponse[] chequeGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse dlPOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncDLOcrResponse[] dlGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse passportPOSTOcr(APIPostAsyncRequests request);

	@GET
	APIPostAsyncPassportOcrResponse[] passportGETocr(@QueryParam("request_id") String request_id);

	@POST
	APIPostAsyncResponse facePOSTvalidation(APIPostAsyncRequests request);

	@GET
	APIPostAsyncFaceValidationResponse[] faceGETvalidaiton(@QueryParam("request_id") String request_id);

}
