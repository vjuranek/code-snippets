package io.github.vjuranek;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/counter")
public class Counter {
    private static final Logger LOG = Logger.getLogger(Counter.class);

    private int count = 0;

    @Inject
    private OpenTelemetry openTelemetry;

    @Inject
    private Tracer tracer;

    TextMapGetter<HttpHeaders> getter = new TextMapGetter<>() {
        @Override
        public String get(HttpHeaders carrier, String key) {
            if (carrier.getRequestHeaders().containsKey(key)) {
                return carrier.getRequestHeader(key).get(0);
            }
            return null;
        }

        @Override
        public Iterable<String> keys(HttpHeaders carrier) {
            return carrier.getRequestHeaders().keySet();
        }
    };

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String count(@jakarta.ws.rs.core.Context HttpHeaders headers) {
        LOG.info(String.format("count called, counter states: %d", count));

        Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator().extract(Context.current(), headers, getter);
        // Automatically use the extracted SpanContext as parent.
        Span serverSpan = tracer.spanBuilder("GET counter/counter").setSpanKind(SpanKind.SERVER).startSpan();

        try (Scope scope = extractedContext.makeCurrent()) {
            return String.valueOf(count);
        } finally {
            serverSpan.end();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String increment(@jakarta.ws.rs.core.Context HttpHeaders headers) {
        LOG.info(String.format("increment called, incrementing to %d", count + 1));

        Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator().extract(Context.current(), headers, getter);
        // Automatically use the extracted SpanContext as parent.
        Span serverSpan = tracer.spanBuilder("POST counter/counter").setSpanKind(SpanKind.SERVER).startSpan();

        try (Scope scope = extractedContext.makeCurrent()) {
            count++;
            return String.valueOf(count);
        } finally {
            serverSpan.end();
        }
    }
}
