// THIS SHIT IS COPY PASTED

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
	private UserRepository uRepo;
	
	private MessageRequest request;
	

	public String getMessageOfTheDay(Long pk, String category) throws Exception
	{
        //User user = uRepo.findByPk(pk);
        
        QuoteDto qDto = new QuoteDto();
        qDto.setCategory(category);
        
        Call<QuoteDto> call = request.getQuote(qDto);
        Response<QuoteDto> response = call.execute();
        QuoteDto quote = response.body();
        
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