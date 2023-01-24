package com.github.vjuranek.camel;

import org.apache.camel.builder.RouteBuilder;

public class FileToFileRoute extends RouteBuilder {

    private final String source;
    private final String destination;

    public FileToFileRoute(String source, String destination) {
        this.source = String.format("file:%s?noop=true", source);
        this.destination = String.format("file:%s", destination);
    }

    public void configure() {
        from(source)
                .log(String.format("Copying from %s to %s", source, destination))
                .to(destination)
                .log("Copied");
    }
}
