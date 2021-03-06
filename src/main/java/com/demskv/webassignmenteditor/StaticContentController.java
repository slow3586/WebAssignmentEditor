package com.demskv.webassignmenteditor;

import java.io.InputStream;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * Delivers static content to the client.
 */
@Path("static/")
public class StaticContentController {

    @Inject
    ServletContext context;

    @GET
    @Path("{path: .*}")
    public Response staticResources(
            @PathParam("path") final String path) {
        final InputStream resource = context.
                getResourceAsStream(
                        String.format("/static/%s", path));
        return null == resource
                ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok().entity(resource).build();
    }
}
