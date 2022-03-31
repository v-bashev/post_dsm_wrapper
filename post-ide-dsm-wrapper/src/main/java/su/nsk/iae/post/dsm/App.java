package su.nsk.iae.post.dsm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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
        setProperty("server.port", String.valueOf(portForNewModule(DEFAULT_MANAGER_URL, moduleName)));
        SpringApplication.run(App.class);
    }

    private static int portForNewModule(String managerUrl, String moduleName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(managerUrl + "/new-module"))
                    .headers("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString("{\"name\": \"" + moduleName + "\"}"))
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(request, BodyHandlers.ofString());
            NewModuleResponse nmr = new ObjectMapper().readValue(response.body(), NewModuleResponse.class);
            return nmr.getFreePort();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}