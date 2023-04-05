package com.jain.schl.svcstdntdtl.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.ws.http.HTTPBinding;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jain.schl.svcstdntdtl.error.CustomErrorHandler;
import com.jain.schl.svcstdntdtl.model.ErrorDetails;
import com.jain.schl.svcstdntdtl.model.FeesDetails;
import com.jain.schl.svcstdntdtl.model.GenericResponse;
import com.jain.schl.svcstdntdtl.model.Pagination;
import com.jain.schl.svcstdntdtl.model.ResponseMetaData;
import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.jain.schl.svcstdntdtl.repo.StudentDetailsRepository;
import com.jain.schl.svcstdntdtl.service.FeeService;

@RestController
@RequestMapping("/fees")
public class FeesDetailsController {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private FeeService feesService;
	

	@PostMapping("/add")
	public GenericResponse add(@RequestBody Map<String,Object> document) throws CustomErrorHandler {
		try {
			Document response = feesService.getFeesDetails((String)document.get("studentId"), (String)document.get("financialYear"), (String)document.get("stdClass"));
			System.out.println("Request " + document);
			document.put("createdDate", new Date());
			//document.put("id", UUID.randomUUID());
			FeesDetails d = feesService.addFeesDetails(document);
			/*ObjectMapper objectMapper = new ObjectMapper();
			Document d = objectMapper.readValue(getClass().getResource("/static/SubmitForm.json"), Document.class);	
			d.put("totalAmout", document.get("totalAmout"));
			System.out.println("Response " + d);
*/			return getGenericResponse(d);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to fetch all students ! please try again later",
					"Somthing went wrong to fetch all students ! please try again later", 400);
		}

	}
	@GetMapping("/getDetails")
	public GenericResponse getFeesDetails(@RequestParam(required=false) String financialYear, 
			@RequestParam(required=true) String id) throws CustomErrorHandler {
		Document document =  feesService.getFeesDetails(id, financialYear, null);
		return getGenericResponseDocument(document);

	}
	private GenericResponse<Document> getGenericResponseDocument(Document document) {
		GenericResponse<Document> genericResponse = new GenericResponse<>();
		ResponseMetaData responseMetaData = new ResponseMetaData();
		responseMetaData.setStatusCode(HttpStatus.OK.value());
		genericResponse.setResponseBody(document);
		return genericResponse;
	}
	private GenericResponse<FeesDetails> getGenericResponse(FeesDetails document) {
		GenericResponse<FeesDetails> genericResponse = new GenericResponse<>();
		ResponseMetaData responseMetaData = new ResponseMetaData();
		responseMetaData.setStatusCode(HttpStatus.OK.value());
		genericResponse.setResponseBody(document);
		return genericResponse;
	}
	private GenericResponse<List<StudentDetails>> getGenericResponseList(List<StudentDetails> studentDetails,
			Pagination pagination) {
		GenericResponse<List<StudentDetails>> genericResponse = new GenericResponse<>();
		genericResponse.setPagination(pagination);
		ResponseMetaData responseMetaData = new ResponseMetaData();
		responseMetaData.setStatusCode(HttpStatus.OK.value());
		genericResponse.setResponseBody(studentDetails);
		return genericResponse;
	}

	private GenericResponse exceptionResponse(String msg, int statusCode) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setClientMessage(msg);
		errorDetails.setInternalMessage(msg);
		errorDetails.setErrorCode("" + statusCode);
		GenericResponse genericResponse = new GenericResponse<>();
		ResponseMetaData responseMetaData = new ResponseMetaData();
		responseMetaData.setErrorDetails(errorDetails);
		responseMetaData.setStatusCode(HttpStatus.BAD_REQUEST.value());
		genericResponse.setResponseMetaData(responseMetaData);
		System.out.println("Exception Response " + genericResponse);

		return genericResponse;
	}
}