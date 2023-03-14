package com.jain.schl.svcstdntdtl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SclmngmntApplication {

	
	public static void main(String[] args) {

		SpringApplication.run(SclmngmntApplication.class, args);
	}

	
   /* MongoDbFactory mongoDbFactory() throws Exception{
    	MongoClientURI uri = new MongoClientURI("mongodb+srv://vipinjainsbim:Admin@cluster0.dmhfssg.mongodb.net/StudentApp?retryWrites=true&w=majority");
        return new SimpleMongoDbFactory(uri);
    }

     @Bean
    MongoTemplate mongoTemplate() throws Exception{
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }*/
}
