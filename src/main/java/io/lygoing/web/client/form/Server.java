package io.lygoing.web.client.form;

import io.lygoing.web.client.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Server extends AbstractVerticle {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        Route route = router.route().handler(BodyHandler.create());
        route.path("/").handler(rc -> {
            System.out.println("Got form with content-type " + rc.request().getHeader("content-type"));

            System.out.println("raw body: " + rc.getBodyAsString());

            HttpServerRequest request = rc.request();

            System.out.println("firstName: " + request.getFormAttribute("firstName"));
            System.out.println("lastName: " + request.getFormAttribute("lastName"));
            System.out.println("male: " + request.getFormAttribute("male"));

            rc.response().end();
        });

        vertx.createHttpServer().requestHandler(router).listen(8088, listenResult -> {
            if (listenResult.failed()) {
                System.out.println("Could not start HTTP server");
                listenResult.cause().printStackTrace();
            } else {
                System.out.println("Server started");
            }
        });
    }
}
