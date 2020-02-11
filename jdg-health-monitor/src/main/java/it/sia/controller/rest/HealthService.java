package it.sia.controller.rest;

import it.sia.model.Health;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Stelios Kousouris
 */
@Path("health")
@Produces(MediaType.APPLICATION_JSON)
public interface HealthService {

    @GET
    @Path("cachecontainer")
    @GetMapping(value = "cachecontainer", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    // @OpenAPIDefinition (info =
    // @Info(
    // title = "JDG CacheContainer Health STATUS",
    // version = "1.0",
    // description = "Status HEALTHY indicates a healthy JDG CacheContainer ready to
    // use its caches"
    // //license = @License(name = "Apache 2.0", url = "http://foo.bar"),
    // //contact = @Contact(url = "http://gigantic-server.com", name = "Fred", email
    // = "Fred@gigagantic-server.com")
    // )
    // )
    public Response getCacheContainerHealth();

    @Path("cachecontainer/node")
    @GET
    @GetMapping(path = "cachecontainer/node", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getNodeHealthDetails();

    @Path("cachecontainer/caches")
    @GET
    // @RequestMapping(path = "cachecontainer/caches")
    @GetMapping(path = "cachecontainer/caches", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getAllCachesHealthDetails();

    @Path("cachecontainer/{cache}")
    @GET
    // @RequestMapping(path = "cachecontainer/{cache}")
    @GetMapping(path = "cachecontainer/{cache}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getCacheHealthDetails(@PathParam("cache") String cacheName);

    @Path("alerts")
    @GET
    @GetMapping(path = "alerts", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getHealthAlerts();

    @Path("{metric}/{threshold}")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    @PutMapping(path = "{metric}/{threshold}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response putHealthMetricThreshold(@PathParam("metric") String metricName,
            @PathParam("threshold") long threshold);

    @Path("server/connection/jmx")
    @GET
    @GetMapping(path = "server/connection/jmx", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getJDGConnectionStatusJMX();

    @Path("server/connection/dmr")
    @GET
    @GetMapping(path = "server/connection/dmr", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response getJDGConnectionStatusDMR();

}