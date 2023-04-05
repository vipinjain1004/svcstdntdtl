package com.jain.schl.svcstdntdtl.repo;

import com.jain.schl.svcstdntdtl.model.FeesDetails;
import com.jain.schl.svcstdntdtl.model.StudentDetails;
import com.mongodb.*;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeesDetailsRepository extends MongoRepository<FeesDetails,String>{	
	@Query("{\n" +
	        "studentId : ?0,\n" +
	        "financialYear : ?1" +
	        "}")
	public List<FeesDetails> getFeesDetailsByStdIdAndFinancialYear(String studentId, String financialYear);
		
	  
}