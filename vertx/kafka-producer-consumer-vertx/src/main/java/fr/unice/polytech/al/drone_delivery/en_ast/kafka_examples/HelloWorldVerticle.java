package fr.unice.polytech.al.drone_delivery.en_ast.kafka_examples;

import io.vertx.core.AbstractVerticle;

/**
 * @author Marc Karassev
 */
public class HelloWorldVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Create an HTTP server which simply returns "Hello World!" to each request.
        vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!")).listen(8080);
    }
}

