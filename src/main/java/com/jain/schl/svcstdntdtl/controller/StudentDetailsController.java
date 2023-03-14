package com.jain.schl.svcstdntdtl.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.ws.http.HTTPBinding;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jain.schl.svcstdntdtl.model.ErrorDetails;
import com.jain.schl.svcstdntdtl.model.GenericResponse;
import com.jain.schl.svcstdntdtl.model.ResponseMetaData;
import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.jain.schl.svcstdntdtl.repo.StudentDetailsRepository;

@RestController
@RequestMapping("/student")
public class StudentDetailsController {
     
	@Autowired
	MongoTemplate mongoTemplate;  
	
    @Autowired 
    private StudentDetailsRepository studentDetailsRepository;
      
    @PostMapping("/add")
    public GenericResponse add(@RequestBody StudentDetails studentDetails) {
    	System.out.println("student add api" + studentDetails);
    	if(StringUtils.isEmpty(studentDetails.getfName()) || 
    			StringUtils.isEmpty(studentDetails.getFatherName()) || 
    			StringUtils.isEmpty(studentDetails.getDateOfBirth())) {
    		return exceptionResponse("Name, Father's Name, Date of birth should not be empty", HttpStatus.BAD_REQUEST.value() );
    	}
    	if(studentDetails.getId() != null) {
    	 	StudentDetails studentExistingDetails = studentDetailsRepository.findById(studentDetails.getId()).get();
        	studentDetails.setDateOfBirth(studentExistingDetails.getDateOfBirth());
        	studentDetails.setfName(studentExistingDetails.getfName());
    	}
        return getGenericResponse(studentDetailsRepository.save(studentDetails));
    }
      
    @GetMapping("/getAll")
    public GenericResponse get(){
    	System.out.println("student getAll api");
    	List<StudentDetails> list = studentDetailsRepository.findAll();
    	System.out.println("end get details" + list);
    	return getGenericResponseList(list);
       
    }
    @DeleteMapping("/{id}/delete")
    public GenericResponse delete(@PathVariable String id){  
    	System.out.println("student delete api");
    	studentDetailsRepository.deleteById(id);
    	StudentDetails studentDetails = new StudentDetails();
    	studentDetails.setId(id);
    	return getGenericResponse(studentDetails);  	       
    }
    @PatchMapping("/{id}/update")
    public GenericResponse update(@RequestBody StudentDetails studentDetails, @PathVariable String id) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException{
    	System.out.println("student update api");
       	StudentDetails studentExistingDetails = studentDetailsRepository.findById(id).get();
    	studentDetails.setDateOfBirth(studentExistingDetails.getDateOfBirth());
    	studentDetails.setId(studentExistingDetails.getId());
    	studentDetails.setfName(studentExistingDetails.getfName());
    	return getGenericResponse(studentDetailsRepository.save(studentDetails));    	
    }
    @GetMapping("/{id}/get")
    public GenericResponse get(@PathVariable String id){
    	System.out.println("student get api");
    	StudentDetails studentDetails = studentDetailsRepository.findById(id).get();
    	System.out.println("end get details" + studentDetails);
    	return getGenericResponse(studentDetails);       
    }
    private GenericResponse<StudentDetails> getGenericResponse(StudentDetails studentDetails){
    	GenericResponse<StudentDetails> genericResponse = new GenericResponse<>();
    	ResponseMetaData responseMetaData = new ResponseMetaData();
    	responseMetaData.setStatusCode(HttpStatus.OK.value());
    	genericResponse.setResponseBody(studentDetails);    	
    	return genericResponse;
    }
    
    private GenericResponse<List<StudentDetails>> getGenericResponseList(List<StudentDetails> studentDetails){
    	GenericResponse<List<StudentDetails>> genericResponse = new GenericResponse<>();
    	ResponseMetaData responseMetaData = new ResponseMetaData();
    	responseMetaData.setStatusCode(HttpStatus.OK.value());
    	genericResponse.setResponseBody(studentDetails);    	
    	return genericResponse;
    }
    private GenericResponse exceptionResponse(String msg, int statusCode) {
    	ErrorDetails errorDetails = new ErrorDetails();
    	errorDetails.setClientMessage(msg);
    	errorDetails.setInternalMessage(msg);
    	errorDetails.setErrorCode(""+statusCode);
    	GenericResponse genericResponse = new GenericResponse<>();
    	ResponseMetaData responseMetaData = new ResponseMetaData();
    	responseMetaData.setErrorDetails(errorDetails);
    	responseMetaData.setStatusCode(HttpStatus.BAD_REQUEST.value());
    	genericResponse.setResponseMetaData(responseMetaData);
    	System.out.println("Exception Response " + genericResponse);
    	return genericResponse;
    }
}