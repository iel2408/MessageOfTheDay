package app.rest.controllers;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.components.MessageOfTheDayComponent;
import app.entities.User;
import app.repositories.UserRepository;

@Component
@Path("/messageOfTheDay")
public class MessageOfTheDayController {

    @Autowired
    private MessageOfTheDayComponent messageOfTheDay;
    
    @Autowired
    private UserRepository userRepo;

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public TwilioReplyDTO sendMessage(
            @FormParam("pk") Long pk,
            @FormParam("category") String category) throws Exception {

    	return messageOfTheDay.sendMessage(pk, category);
    }

    @POST
    @Path("/sendToAll")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendToAll(@FormParam("category") String category) throws Exception {

        List<User> users = userRepo.findAll();
        int messagesSent = 0;

        for (User user : users) 
        {
        	messageOfTheDay.sendMessage(user.getPk(), category);
            messagesSent++;
        }

        return messagesSent + " messages sent successfully";
    }
}