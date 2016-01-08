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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SUN on 18/12/2015.
 */
public class DroneMessageVerticle extends AbstractVerticle {

        private static Map<Integer, Drone> drones = new LinkedHashMap<Integer, Drone>();

        private void createDatas() {
                Drone drone1 = new Drone(1, 1.0, 1.0,1.0, 99, "take off");
                Drone drone2 = new Drone(2, 1.0, 1.0,1.0, 99, "take off");
                Drone drone3 = new Drone(3, 1.0, 1.0,1.0, 99, "take off");
                Drone drone4 = new Drone(4, 1.0, 1.0,1.0, 99, "take off");
                Drone drone5 = new Drone(5, 1.0, 1.0,1.0, 99, "take off");
                drones.put(1, drone1);
                drones.put(2, drone2);
                drones.put(3, drone3);
                drones.put(4, drone4);
                drones.put(5, drone5);
        }

        @Override
        /*
        * The start method is called when the verticle is deployed
        **/
        public void start(Future<Void> fut) {
                HttpServer server = vertx.createHttpServer();
                //Create a router
                Router router = Router.router(vertx);
                Route route1 = router.route(HttpMethod.GET, "/app")
                        .produces("application/json");

                route1.handler(routingContext -> {
                        HttpServerResponse response = routingContext.response();
                        response.putHeader("content-type", "application/json");
                        JsonArray jsonArray = new JsonArray();
                        createDatas();
                        for(Integer i : drones.keySet()) {
                                JsonObject json = new JsonObject();
                                Drone d = drones.get(i);
                                json.put("id", d.getId());
                                json.put("lat", d.getLat());
                                json.put("lon", d.getLon());
                                json.put("fuel", d.getFuel());
                                json.put("event", d.getEvent());
                                jsonArray.add(json);
                        }
                        response.end(jsonArray.toString());
                });
                server.requestHandler(router::accept).listen(8080, result -> {
                        if (result.succeeded()) {
                                fut.complete();
                        } else {
                                fut.fail(result.cause());
                        }
                });
        }

}
