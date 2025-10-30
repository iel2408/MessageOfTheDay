// TO BE EDITED

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

@Component
public class MessageOfTheDay {
	
	@Autowired
	private UserRepository userRepo;
	
	private MessageRequest request;
	

	public String getMessageOfTheDay(Long pk, String category) throws Exception
	{
//        QuoteDTO qDto = new QuoteDTO();
//        qDto.setCategory(category);
		
		QuoteDTO q = new QuoteDTO(category);
        Call<QuoteDTO> call = request.getQuote(q);
        Response<QuoteDTO> response = call.execute();
        QuoteDTO quote = response.body();
        
        return quote.getMessage();
	}
	
	@PostConstruct
	public void init() throws Exception
	{
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.create();
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:8080")
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();
		
		request = retrofit.create(MessageRequest.class);
	}	
}