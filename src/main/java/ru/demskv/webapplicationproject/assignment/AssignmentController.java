
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.demskv.webapplicationproject.JsonUtil;


@Path("api/assignment/")
public class AssignmentController {
    final static Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    
    @EJB(beanName="AssignmentServiceEJB")
    private AssignmentServiceLocal service;
    
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @Min(0) @QueryParam("from") int from,
            @Min(1) @Max(50) @QueryParam("limit") int limit,
            @QueryParam("order_by") String orderBy,
            @QueryParam("id") Integer filterId,
            @QueryParam("topic") String filterTopic,
            @QueryParam("text") String filterText,
            @QueryParam("author") Integer filterAuthor,
            @QueryParam("executeby") String filterExecuteby,
            @QueryParam("executeattr") String filterExecuteattr,
            @QueryParam("executors") Set<Integer> filterExecutors
    ) {
        boolean orderDesc = false;
        String columnName = "id";
        if(orderBy!=null && !orderBy.isBlank()){
            orderDesc = !orderBy.substring(0, 1).equals("-");
            columnName = orderBy.substring(1);
        }
        return Response.ok()
                .header("Content-Range", "items "+from+"-"+(from+limit)+"/"+
                        service.countAll(filterId, filterTopic, filterText, filterAuthor, 
                                filterExecuteby, filterExecuteattr, filterExecutors))
                .entity(JsonUtil.tojson((service.findAll(
                        from, limit, columnName, orderDesc, 
                        filterId, filterTopic, filterText, filterAuthor,
                        filterExecuteby, filterExecuteattr, filterExecutors))))
                .build();
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@Valid AssignmentDTO assignment) {
        service.create(assignment);
        return Response.ok().build();
    }
    
    @PUT
    @Path("{id}")
    public Response put(@Valid AssignmentDTO assignment) {
        service.update(assignment);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") @Min(1) Integer id) {
        service.deleteById(id);
        return Response.ok().build();
    }
}