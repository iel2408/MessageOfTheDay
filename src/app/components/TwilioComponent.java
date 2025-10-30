package app.components;

import java.util.Base64;
import org.springframework.stereotype.Component;
import app.entities.TwilioReply;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Component
public class TwilioComponent {
	
    private final String msgsid = "MG7223c980e17d977693558763fdd330b1";
    
    private final String acctId = "AC6aef1f2addc6e3c109f2907a065e1c8b";
    private final String creds = "50b79ac51db9a18b183c7e6a3a723fdf";
    
    private final String url = "https://api.twilio.com/2010-04-01/Accounts/AC6aef1f2addc6e3c109f2907a065e1c8b/Messages.json";

    // add a new parameter that takes in message
    // should be message, cpnumber
    public TwilioReply sendSMS(String name, String quote, String cpNumber) throws Exception {
        
    	Retrofit retrofit = new Retrofit.Builder()
	               .baseUrl("http://localhost:8080")
	               .build();

		
		byte[] encodedAuth= Base64.getEncoder().encode((acctId+":"+creds).getBytes());
		final String authorization = "Basic " + new String(encodedAuth);
		
		TwilioRequests req = retrofit.create(TwilioRequests.class);
    	
    	String message = "Hello " + name + ", " + quote;
    	
		Call<ResponseBody> call = req.testSMS("+18144849468", 	
								msgsid,
								"Twilio test",
								authorization,
								url);
		
		Response<ResponseBody> resp = call.execute();
		
		System.out.println(resp.code());

        if (resp.code() == 201) 
        {
        	System.out.println(resp.body().string());
        	return new TwilioReply(name, message);
        }
        else 
        {
        	System.out.println(resp.errorBody().string());
        	return new TwilioReply(name, "Failed to send message");
        }
    }


}