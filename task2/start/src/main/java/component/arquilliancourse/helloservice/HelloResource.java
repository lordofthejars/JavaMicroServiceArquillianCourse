package component.arquilliancourse.helloservice;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path( "/greetings" )
public class HelloResource {

    @Inject
    GreetingDAO greetingDAO;

    @Inject
    GreetingService greetingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGreeting (){
        return Response.ok(greetingDAO.findAll(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path( "{id}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getGreetingById( @PathParam( "id" ) String greetingId ){
        try {
            return Response.ok( greetingDAO.findByID( greetingId.toLowerCase() ), MediaType.APPLICATION_JSON ).build();
        }catch ( NoSuchElementException ex ){
            ex.printStackTrace();
            return Response.status( 404 ).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGreeting( Greeting greeting ){
        try {
            greetingService.validateAndSaveGreeting( greeting );
            return Response.ok().build();
        }catch ( ValueException ex ){
            ex.printStackTrace();
            return Response.status( 400 ).build();
        }
    }

}
