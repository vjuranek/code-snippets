import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Path("/proxy")
public class CounterProxy {
    private static final Logger LOG = Logger.getLogger(CounterProxy.class);

    private HttpClient client = HttpClient.newBuilder().build();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response count() throws Exception {
        LOG.info("hello called");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://counter:8080/counter"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Response
                .status(response.statusCode())
                .entity(String.format("Counter state: %s", response.body()))
                .build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response increment() throws Exception {
        LOG.info(String.format("increment called"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://counter:8080/counter"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Response
                .status(response.statusCode())
                .entity(String.format("New counter state: %s", response.body()))
                .build();
    }

}
