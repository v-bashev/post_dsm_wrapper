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
import java.util.Properties;
import static java.lang.System.setProperty;

@SpringBootApplication
public class App {

    public static void main(String[] args){
        try {
            Properties properties = new Properties();
            properties.load(App.class.getClassLoader().getResourceAsStream("dsm.properties"));
            String managerAddress = properties.getProperty("manager.address");
            String dsmName = properties.getProperty("dsm.name");
            for (int i = 0; i < args.length; i++) {
                if ("-name".equals(args[i])) {
                    dsmName = args[++i];
                }
            }
            int freePort = ServerUtils.findFreePort();
            registerModule(managerAddress, dsmName, freePort);
            setProperty("server.port", String.valueOf(freePort));
            SpringApplication.run(App.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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