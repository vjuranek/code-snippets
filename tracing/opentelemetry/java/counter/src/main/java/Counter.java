import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/counter")
public class Counter {
    private static final Logger LOG = Logger.getLogger(Counter.class);

    private int count = 0;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {
        LOG.info(String.format("count called, counter states: %d", count));
        return String.valueOf(count);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String increment() {
        LOG.info(String.format("increment called, incrementing to %d", count + 1));
        count++;
        return String.valueOf(count);
    }
}
