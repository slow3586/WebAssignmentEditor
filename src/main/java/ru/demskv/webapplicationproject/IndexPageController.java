package ru.demskv.webapplicationproject;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Delivers the index page to the client.
 */
@Path("")
public class IndexPageController {
@Inject
    ServletContext context;
    
    @GET
    @Path("")
    public Response staticResources() {
        final InputStream resource = context.
                getResourceAsStream("/static/index.html");
        return null == resource
                ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok().entity(resource).build();
    }
}
