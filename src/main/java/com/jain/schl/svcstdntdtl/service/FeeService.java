package com.jain.schl.svcstdntdtl.service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.commons.collections4.ListUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.data.mongodb.core.aggregation.StringOperators.ToUpper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jain.schl.svcstdntdtl.error.CustomErrorHandler;
import com.jain.schl.svcstdntdtl.model.ErrorDetails;
import com.jain.schl.svcstdntdtl.model.Fees;
import com.jain.schl.svcstdntdtl.model.FeesDetails;
import com.jain.schl.svcstdntdtl.model.GenericResponse;
import com.jain.schl.svcstdntdtl.model.Pagination;
import com.jain.schl.svcstdntdtl.model.ResponseMetaData;
import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.jain.schl.svcstdntdtl.repo.FeesDetailsRepository;

import lombok.patcher.inject.LiveInjector.LibInstrument;

@Service
public class FeeService {

	@Autowired
	private FeesDetailsRepository feesDetailsRepository;
	
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	static String[] months = new DateFormatSymbols().getShortMonths();
	private static List<String> MONTHS = Arrays.asList("JUL", "AUG", "SEP", "OCT", "NOV", "DEC", "JAN", "FEB", "MAR",
			"APR");

	@PostConstruct
	public void initilization() {
		 
	}

	public Document getFeesDetails(String stdId, String financialYear, String stdClass) {
		try {
			if (StringUtils.isEmpty(financialYear)) {
				financialYear = "2023-24";
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			Document d = objectMapper.readValue(getClass().getResource("/static/FeesDetails.json"), Document.class);
			d.put("financialYear", financialYear);

			List<FeesDetails> response = getFeesDetailsByStdIdAndFinancialYear(stdId, financialYear);
			Map<String, Object> feesDetailsBody = new LinkedHashMap<>();
			feesDetailsBody.put("financialYear", financialYear);
			feesDetailsBody.put("monthlyFees", 100);
			feesDetailsBody.put("admissionFees", 500);
			feesDetailsBody.put("examFees", 50);
			feesDetailsBody.put("classStd", stdClass);
			// 
			List<Map<String,Object>> list = new ArrayList<>();
			FeesDetails fd = response.stream()
					.filter(item1 -> item1.getFeesDetails().stream().anyMatch(it -> it.equals("ADMISSION"))).findAny()
					.orElse(null);
			List<String> feeFor = new ArrayList<>();
			
			response.stream().forEach(item -> {
				
				for (Fees fee : item.getFeesDetails()) {
					
					Map<String, Object> fdMap = new LinkedHashMap<>();
					fdMap.put("id", UUID.randomUUID());
					fdMap.put("feesFor", fee.getFeesFor());
					fdMap.put("dt", simpleDateFormat.format(item.createdDate));
					fdMap.put("userId", item.userId);
					fdMap.put("status", "SUBMITTED");
					fdMap.put("amount", fee.getAmount());
					list.add(fdMap);
					feeFor.add(fee.getFeesFor());
				}
			});
			if(!feeFor.contains("ADMISSION")) {
				Map<String, Object> fdMap = new LinkedHashMap<>();
				fdMap.put("id", UUID.randomUUID());
				fdMap.put("feesFor", "ADMISSION");
				fdMap.put("dt", "");
				fdMap.put("userId", "");
				fdMap.put("status", "PENDING");
				fdMap.put("amount", 500);
				list.add(fdMap);
				feeFor.remove("ADMISSION");
			}

			if(!feeFor.contains("EXAM")) {
				Map<String, Object> fdMap = new LinkedHashMap<>();
				fdMap.put("id", UUID.randomUUID());
				fdMap.put("feesFor", "EXAM");
				fdMap.put("dt", "");
				fdMap.put("userId", "");
				fdMap.put("status", "PENDING");
				fdMap.put("amount", 50);
				list.add(fdMap);
				feeFor.remove("EXAM");
			}
			
			List<String> notContainMonths = MONTHS.stream().filter(item->!feeFor.contains(item)).collect(Collectors.toList());
			notContainMonths.stream().forEach(item ->{
				
				Map<String, Object> fdMap = new LinkedHashMap<>();
				fdMap.put("id", UUID.randomUUID());
				fdMap.put("feesFor", item);
				fdMap.put("dt", "");
				fdMap.put("userId", "");
				fdMap.put("status", "PENDING");
				fdMap.put("amount", 100);
				list.add(fdMap);				
			});
			
			
			List<List<Map<String,Object>>> partitionList =  ListUtils.partition(list, 4);
			System.out.println("Response " + d);
			feesDetailsBody.put("feesDetail", partitionList);
			ObjectMapper obj = new ObjectMapper();
			Document dd = obj.convertValue(feesDetailsBody, Document.class);
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to fetch all students ! please try again later",
					"Somthing went wrong to fetch all students ! please try again later", 400);
		}
	}

	public List<FeesDetails> getFeesDetailsByStdIdAndFinancialYear(String stdId, String financialYear) {
		try {
			return feesDetailsRepository.getFeesDetailsByStdIdAndFinancialYear(stdId, financialYear);

		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomErrorHandler("Somthing went wrong to fetch all students ! please try again later",
					"Somthing went wrong to fetch all students ! please try again later", 400);
		}
	}

	public FeesDetails addFeesDetails(Map<String, Object> document) {
		System.out.println("FeesDetails Request " + document);
		List<FeesDetails> response = getFeesDetailsByStdIdAndFinancialYear((String) document.get("studentId"),
				(String) document.get("financialYear"));
		// response.stream().map(item->item.getFeesDetails()).filter(item->
		// item.stream().map)
		List<String> listOfFeesFor = response.stream()
				.flatMap(item -> item.getFeesDetails().stream().map(item1 -> item1.feesFor))
				.collect(Collectors.toList());
		List<String> monthsNotSubmitted = MONTHS.stream().filter(item -> !listOfFeesFor.contains(item))
				.collect(Collectors.toList());
		System.out.println("Month not submitted " + monthsNotSubmitted);
		List<Map> feesDetails = new ArrayList<>();
		for (Object obj : (List) document.get("feesDetails")) {
			Map feeDetailObj = (Map<String, Object>) obj;
			int totalAmount = (int) feeDetailObj.get("amount");
			if (feeDetailObj.get("feesFor").equals("MONTHLY") && totalAmount != 0) {
				if (!CollectionUtils.isEmpty(monthsNotSubmitted)) {
					int count = (int) feeDetailObj.get("count");
					int monthlyFees = totalAmount / count;
					for (int i = 0; i < count; i++) {
						Map<String, Object> submitMonthlyFees = new LinkedHashMap<>();
						submitMonthlyFees.put("feesFor", monthsNotSubmitted.get(i));
						submitMonthlyFees.put("amount", monthlyFees);
						feesDetails.add(submitMonthlyFees);
					}
				}
			} else if (feeDetailObj.get("feesFor").equals("EXAM") && totalAmount != 0) {
				if ((int) feeDetailObj.get("amount") != 0) {
					feesDetails.add(feeDetailObj);
				}
			} else if (feeDetailObj.get("feesFor").equals("ADMISSION") && totalAmount != 0) {
				if ((int) feeDetailObj.get("amount") != 0) {
					feesDetails.add(feeDetailObj);
				}
			}
		}
		document.put("feesDetails", feesDetails);
		ObjectMapper objectMapper = new ObjectMapper();
		FeesDetails fees = objectMapper.convertValue(document, FeesDetails.class);
		FeesDetails response1 = feesDetailsRepository.save(fees);
		System.out.println("FeesDetails Response " + response1);
		return response1;
	}

	private GenericResponse<Document> getGenericResponse(Document document) {
		GenericResponse<Document> genericResponse = new GenericResponse<>();
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
