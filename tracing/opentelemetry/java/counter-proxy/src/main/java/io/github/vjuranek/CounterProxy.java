package io.github.vjuranek;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapSetter;
import jakarta.inject.Inject;
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

    @Inject
    private OpenTelemetry openTelemetry;

    @Inject
    private Tracer tracer;

    private HttpClient client = HttpClient.newBuilder().build();
    private TextMapSetter<HttpRequest.Builder> setter = new TextMapSetter<>() {
        @Override
        public void set(HttpRequest.Builder carrier, String key, String value) {
            carrier.header(key, value);
        }
    };

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response count() throws Exception {
        LOG.info("hello called");

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder().uri(new URI("http://counter:8080/counter")).GET();
        Span clientSpan = tracer.spanBuilder("GET proxy/counter").setSpanKind(SpanKind.CLIENT).startSpan();

        try (Scope scope = clientSpan.makeCurrent()) {
            openTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), reqBuilder, setter);
            HttpResponse<String> response = client.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
            return Response
                    .status(response.statusCode())
                    .entity(String.format("Counter state: %s", response.body()))
                    .build();
        } finally {
            clientSpan.end();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response increment() throws Exception {
        LOG.info(String.format("increment called"));

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
                .uri(new URI("http://counter:8080/counter"))
                .POST(HttpRequest.BodyPublishers.ofString(""));
        Span clientSpan = tracer.spanBuilder("POST proxy/counter").setSpanKind(SpanKind.CLIENT).startSpan();

        try (Scope scope = clientSpan.makeCurrent()) {
            openTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), reqBuilder, setter);
            HttpResponse<String> response = client.send(reqBuilder.build(), HttpResponse.BodyHandlers.ofString());
            return Response
                    .status(response.statusCode())
                    .entity(String.format("New counter state: %s", response.body()))
                    .build();
        } finally {
            clientSpan.end();
        }
    }

}
