package io.lygoing.web.client.form;

import io.lygoing.web.client.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.ProxyOptions;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Client extends AbstractVerticle {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        WebClient client = WebClient.create(vertx,
                new WebClientOptions().setProxyOptions(new ProxyOptions().setHost("localhost").setPort(8888)));

        MultiMap form = MultiMap.caseInsensitiveMultiMap();
        form.add("firstName", "Dale");
        form.add("lastName", "Cooper");
        form.add("male", "true");

        client.post(8088, "localhost", "/")
                .putHeader("content-type", "application/x-www-form-urlencoded")
                .sendForm(form, ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<Buffer> response = ar.result();
                        System.out.println("Got HTTP response with status " + response.statusCode());
                    } else {
                        ar.cause().printStackTrace();
                    }
                });
    }
}
