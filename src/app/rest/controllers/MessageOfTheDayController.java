//COPY POASTED

package app.rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.MessageOfTheDay;
import app.components.TwilioComponent;
import app.entities.TwilioReply;
import app.entities.User;
import app.repositories.UserRepository;

@Component
@Path("/motd")
public class MessageOfTheDayController {

    @Autowired
    private MessageOfTheDay motd;

    @Autowired
    private TwilioComponent twilio;
    
    @Autowired
    private UserRepository userRepo;

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public TwilioReply sendMessage(
            @FormParam("pk") Long pk,
            @FormParam("category") String category) throws Exception {

        String message = motd.getMessageOfTheDay(pk, category);
        User user = userRepo.findByPk(pk);
        TwilioReply reply = twilio.sendSMS(user.getName(), message, user.getCellphoneNumber());

        return reply;
    }

    @POST
    @Path("/sendToAll")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendToAll(@FormParam("category") String category) throws Exception {

        List<User> users = userRepo.findAll();
        int successes = 0;

        for (User user : users) 
        {
            String message = motd.getMessageOfTheDay(user.getPk(), category);
            twilio.sendSMS(user.getName(), message, user.getCellphoneNumber());
            successes++;
        }

        return successes + " messages sent successfully.";
    }
}