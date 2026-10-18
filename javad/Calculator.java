
import com.sun.net.httpserver.HttpServer;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.Executors;

public class Calculator {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {
            byte[] html = Files.readAllBytes(
                Paths.get("/app/index.html"));

            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8");

            exchange.sendResponseHeaders(200, html.length);
            exchange.getResponseBody().write(html);
            exchange.close();
        });

        server.createContext("/calculate", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String result;

            try {
                String[] parts = query.split("&");
                double a = Double.parseDouble(
                    parts[0].split("=")[1]);
                double b = Double.parseDouble(
                    parts[1].split("=")[1]);
                String op = parts[2].split("=")[1];

                switch (op) {
                    case "add": result = "" + (a + b); break;
                    case "sub": result = "" + (a - b); break;
                    case "mul": result = "" + (a * b); break;
                    case "div":
                        result = b == 0
                            ? "Cannot divide by zero"
                            : "" + (a / b);
                        break;
                    default: result = "Invalid operation";
                }
            } catch (Exception e) {
                result = "Invalid input";
            }

            byte[] response = result.getBytes("UTF-8");
            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();

        System.out.println("Calculator running on port 8080");
    }
}
