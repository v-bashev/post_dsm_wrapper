package su.nsk.iae.post.dsm;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import su.nsk.iae.post.dsm.manager.common.Request;
import su.nsk.iae.post.dsm.manager.common.Response;
import su.nsk.iae.post.dsm.server.ServerLauncher;

public class AppLauncher {
	//TODO Rename constants DSM_NAME (name of your dsm) DSM_DIRECTORY (directory for generated files by your generator)
	public static final String DSM_NAME = "ST_dsm";
	public static final String DSM_DIRECTORY = "st";

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8025;

	private static ServerLauncher serverLauncher;

	public static void main(String[] args) {

		int port = DEFAULT_PORT;

		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI("http://127.0.0.1:8080"))
					.headers("REQUEST", Request.NEW_MODULE.toString())
					.GET()
					.build();
			HttpResponse<String> response = HttpClient.newBuilder()
					.proxy(ProxySelector.getDefault())
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());
			JsonElement jsonTree = new JsonParser().parse(response.body());
			if (jsonTree.isJsonObject()) {
				JsonObject json = jsonTree.getAsJsonObject();
				JsonElement element = json.get(Response.ResponseType.FREE_PORT.toString());
				port = element.getAsInt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		serverLauncher = new ServerLauncher();
		System.out.println("Starting server at " + DEFAULT_HOST + ":" + port);
		serverLauncher.start(DEFAULT_HOST, port);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));

		System.out.println("[" + DSM_NAME + "] Server started");
		try {
			System.in.read();
		} catch (IOException e) {
			System.out.println("[" + DSM_NAME + "] Caught an exception");
			e.printStackTrace();
		} finally {
			stop();
		}
	}

	static void stop() {
		System.out.println("[" + DSM_NAME + "] Server stoped");
		if (serverLauncher != null) {
			serverLauncher.shutdown();
			serverLauncher = null;
		}
	}
}