

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.Server;
import com.mongodb.connection.ServerVersion;

public class MongoDb {
public static void main(String args[]) {
	ConnectionString connectionString = new ConnectionString("mongodb+srv://vipinjainsbim:Admin@cluster0.dmhfssg.mongodb.net/?retryWrites=true&w=majority");
	MongoClientSettings settings = MongoClientSettings.builder()
	        .applyConnectionString(connectionString)
	        .build();
	MongoClient mongoClient = MongoClients.create(settings);
	MongoDatabase database = mongoClient.getDatabase("StudentApp");
	MongoCollection<Document> doc = database.getCollection("StudentDetails");
	System.out.println("documentaiont of doc " + doc.find().first());
}
}
