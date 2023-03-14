package com.jain.schl.svcstdntdtl.repo;

import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.mongodb.*;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailsRepository extends MongoRepository<StudentDetails,String>{
	  
}