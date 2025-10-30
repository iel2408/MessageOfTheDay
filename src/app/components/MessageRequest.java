package app.components;

import app.rest.controllers.QuoteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageRequest 
{
	@POST("http://localhost:9999/messageServer/getQuote")
    Call<QuoteDTO> getQuote(@Body QuoteDTO parameterObject);
}