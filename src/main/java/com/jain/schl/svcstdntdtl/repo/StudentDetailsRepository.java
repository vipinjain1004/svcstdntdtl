package com.jain.schl.svcstdntdtl.repo;

import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.mongodb.*;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailsRepository extends MongoRepository<StudentDetails,String>{
	
	@Query("{\n" +
	        "fName : { $regex : ?0, $options: 'i'},\n" +
	        "stdClass : ?1" +
	        "}")
	List<StudentDetails> findByFNameAndStdClassRegex(String fName, String stdClass);
	
	@Query("{\n" +
	        "fName : { $regex : ?0, $options: 'i'},\n" +	        
	        "}")
	List<StudentDetails> findByFNameRegex(String fName);
	
	@Query("{\n" +	     
	        "stdClass : ?0" +
	        "}")
	List<StudentDetails> findByClass(String stdClass);
	

	  

	  
}