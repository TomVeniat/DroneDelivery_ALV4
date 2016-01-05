package fr.unice.polytech.al.drone_delivery.en_ast.examples.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * @author Marc Karassev
 */
public class Server extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        // TODO
        router.route().handler(routingContext -> routingContext.response().putHeader("content-type", "text/html").end("Hello World!"));

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}

