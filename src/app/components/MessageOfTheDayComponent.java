package app.components;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import app.entities.User;
import app.repositories.UserRepository;
import app.rest.controllers.QuoteDTO;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import app.rest.controllers.TwilioReplyDTO;
 

@Component
public class MessageOfTheDayComponent {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private TwilioComponent twilioComponent;
	
	private MessageRequest request;
	
	
	@PostConstruct
	public void init() throws Exception
	{
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.create();
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:9999/")				
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();
		
		request= retrofit.create(MessageRequest.class);
	}
	


	public TwilioReplyDTO sendMessage(Long pk, String category) throws Exception
	{
		User user = userRepo.findByPk(pk);
		if (user == null) {
	        throw new IllegalArgumentException("User not found for id " + pk);
	    }

		QuoteDTO q = new QuoteDTO(category);
		Call<QuoteDTO> call = request.getQuote(q);
		Response<QuoteDTO> response = call.execute();
		
		QuoteDTO quote = response.body();
		
		String message = "Hello " + user.getName() + ", " + quote.getMessage();
		
        TwilioReplyDTO reply = twilioComponent.sendSMS(message, user.getName());
        return reply;
        
	}
}
