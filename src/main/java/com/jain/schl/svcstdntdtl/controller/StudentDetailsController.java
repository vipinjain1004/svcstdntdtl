package com.jain.schl.svcstdntdtl.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jain.schl.svcstdntdtl.error.CustomErrorHandler;
import com.jain.schl.svcstdntdtl.model.ErrorDetails;
import com.jain.schl.svcstdntdtl.model.GenericResponse;
import com.jain.schl.svcstdntdtl.model.Pagination;
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
	public GenericResponse add(@RequestBody StudentDetails studentDetails) throws CustomErrorHandler {
		try {
			System.out.println("student add api" + studentDetails);
			if (StringUtils.isEmpty(studentDetails.getfName()) || StringUtils.isEmpty(studentDetails.getFatherName())
					|| StringUtils.isEmpty(studentDetails.getDateOfBirth())) {
				return exceptionResponse("Name, Father's Name, Date of birth should not be empty",
						HttpStatus.BAD_REQUEST.value());
			}
			if (studentDetails.getId() != null) {
				StudentDetails studentExistingDetails = studentDetailsRepository.findById(studentDetails.getId()).get();
				studentDetails.setDateOfBirth(studentExistingDetails.getDateOfBirth());
				studentDetails.setfName(studentExistingDetails.getfName());
			}
			return getGenericResponse(studentDetailsRepository.save(studentDetails));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to fetch all students ! please try again later",
					"Somthing went wrong to fetch all students ! please try again later", 400);
		}

	}

	@GetMapping("/getAll")
	public GenericResponse get() throws InterruptedException, CustomErrorHandler {
		try {
			System.out.println("student getAll api");
			List<StudentDetails> list = studentDetailsRepository.findAll();
			System.out.println("end get details" + list);
			return getGenericResponseList(list, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Something went wrong to get student details ! please try again later",
					"Something went wrong to get student details ! please try again later", 400);
		}

	}

	@GetMapping("/getAllByParam")
	public GenericResponse getAllByNameClass(@RequestParam(required = false) String fName,
			@RequestParam(required = false) String stdClass,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = "10") int limit)
			throws InterruptedException, CustomErrorHandler {
		try {

			System.out.println("student getAll api");
			List<StudentDetails> list = null;
			long totalCount = 0;
			if (!StringUtils.isEmpty(fName) && !StringUtils.isEmpty(stdClass)) {
				fName = ".*" + fName + ".*";
				list = studentDetailsRepository.findByFNameAndStdClassRegex(fName, stdClass);
			} else if (!StringUtils.isEmpty(fName)) {
				fName = ".*" + fName + ".*";
				list = studentDetailsRepository.findByFNameRegex(fName);
			} else if (!StringUtils.isEmpty(stdClass)) {
				list = studentDetailsRepository.findByClass(stdClass);
			} else {
				list = studentDetailsRepository.findAll();
			}
			totalCount = list.size();
			if (totalCount > 0) {
				list = list.stream().skip(offset).limit(limit).collect(Collectors.toList());
			}
			Pagination pagination = new Pagination();
			pagination.setTotal(totalCount);
			pagination.setLimit(limit);
			pagination.setOffset(offset);

			System.out.println("end get details" + list);
			return getGenericResponseList(list, pagination);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Something went wrong to get student details ! please try again later",
					"Something went wrong to get student details ! please try again later", 400);
		}

	}

	@DeleteMapping("/{id}/delete")
	public GenericResponse delete(@PathVariable String id) throws CustomErrorHandler {
		try {
			System.out.println("student delete api");
			studentDetailsRepository.deleteById(id);
			StudentDetails studentDetails = new StudentDetails();
			studentDetails.setId(id);
			return getGenericResponse(studentDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to delete student! please try again later",
					"Somthing went wrong to delete student! please try again later", 400);
		}

	}

	@PatchMapping("/{id}/update")
	public GenericResponse update(@RequestBody StudentDetails studentDetails, @PathVariable String id)
			throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, CustomErrorHandler {
		try {
			System.out.println("student update api");
			StudentDetails studentExistingDetails = studentDetailsRepository.findById(id).get();
			studentDetails.setDateOfBirth(studentExistingDetails.getDateOfBirth());
			studentDetails.setId(studentExistingDetails.getId());
			studentDetails.setfName(studentExistingDetails.getfName());
			return getGenericResponse(studentDetailsRepository.save(studentDetails));
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to update data ! please try again later",
					"Somthing went wrong to update data ! please try again later", 400);
		}

	}

	@GetMapping("/{id}/get")
	public GenericResponse get(@PathVariable String id) throws CustomErrorHandler {
		try {

			System.out.println("student get api");
			StudentDetails studentDetails = studentDetailsRepository.findById(id).get();
			System.out.println("end get details" + studentDetails);
			return getGenericResponse(studentDetails);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Something went wrong to get student details",
					"Something went wrong to get student details", 400);
		}
	}

	private GenericResponse<StudentDetails> getGenericResponse(StudentDetails studentDetails) {
		GenericResponse<StudentDetails> genericResponse = new GenericResponse<>();
		ResponseMetaData responseMetaData = new ResponseMetaData();
		responseMetaData.setStatusCode(HttpStatus.OK.value());
		genericResponse.setResponseBody(studentDetails);
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