package com.idfy.qa.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

@Path("/v2/tasks/sync")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface SyncCalls {

	@POST
	APIPostVoterResponse voterOcr(APIPostRequest request);

	@POST
	APIPostPanResponse panOcr(APIPostRequest request);

	@POST
	APIPostAadharResponse aadharOcr(APIPostRequest request);

	@POST
	APIPostAadharMaskingResponse aadharMasking(APIPostRequest request);

	@POST
	APIPostDLResponse dlOcr(APIPostRequest request);

	@POST
	APIPostChequeResponse chequeOcr(APIPostRequest request);

	@POST
	APIPostFaceValidationResponse faceValidation(APIPostRequest request);

	@POST
	APIPostFaceCompareResponse faceCompare(APIPostRequest request);

	@POST
	APIPostPassportResponse passportOcr(APIPostRequest request);

}
