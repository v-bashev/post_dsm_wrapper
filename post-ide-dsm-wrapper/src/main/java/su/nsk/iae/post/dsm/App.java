package su.nsk.iae.post.dsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;

@SpringBootApplication
public class App {

    private final static String DEFAULT_NAME = "";
    private final static String DEFAULT_MANAGER_URL = "http://127.0.0.1:7788";

    public static void main(String[] args){
        String moduleName = DEFAULT_NAME;
        for (int i = 0; i < args.length; i++) {
            if ("-name" .equals(args[i])) {
                moduleName = args[++i];
            }
        }
        int freePort = ServerUtils.findFreePort();
        try {
            registerModule(DEFAULT_MANAGER_URL, moduleName, freePort);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        setProperty("server.port", String.valueOf(freePort));
        SpringApplication.run(App.class);
        System.out.println("Running on port " + getProperty("server.port"));
    }

    private static void registerModule(String managerUrl, String name, int port) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(managerUrl + "/new-module"))
                .headers("Content-Type", "application/json")
                .POST(BodyPublishers.ofString("{\"name\": \"" + name + "\", \"port\": " + port + "}"))
                .build();
        HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, BodyHandlers.ofString());
    }
}