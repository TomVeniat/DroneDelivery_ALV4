package io.vertx.drone.messagehandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SUN on 18/12/2015.
 */
public class DroneMessageVerticle extends AbstractVerticle {

        @Override
        /*
        * The start method is called when the verticle is deployed
        **/
        public void start(Future<Void> fut) {
                HttpServer server = vertx.createHttpServer();
                //Create a router
                Router router = Router.router(vertx);
                router.route().handler(BodyHandler.create());
                router.post("/drone_message").handler(routingContext -> {
                        //Get the messages send by the drone
                        JsonObject req = routingContext.getBodyAsJson();
                        System.out.println(req);
                        //Check message validity
                        Drone drone = DroneMessageParser.parse(req);
                        System.out.println(drone);
                        if(drone != null) {
                                //The message is valid, push in the topic
                                new KafkaPusher().pushMessage(drone);
                        }
                        HttpServerResponse response = routingContext.response();
                        response.putHeader("content-type", "application/json");
                        response.end();

                });

                server.requestHandler(router::accept).listen(9000, result -> {
                        if (result.succeeded()) {
                                fut.complete();
                        } else {
                                fut.fail(result.cause());
                        }
                });
        }

}
