package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kong.unirest.json.JSONObject;
import service.PriceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class PriceHttpHandler implements HttpHandler {
    private String endpoint;
    private PriceService service;

    public PriceHttpHandler(String endpoint) {
        this.endpoint = endpoint;
        service = new PriceService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Response response = new Response();

        JSONObject parsedRequest = parseRequestBody(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            sendResponse(exchange, response);
            return;
        }

        switch (this.endpoint) {
            case "price-history":
                response = service.getPricesInPeriod(parsedRequest.getString("symbol"), parsedRequest.getLong("from"), parsedRequest.getLong("to"));
                break;
            case "current-price":
                response = service.getCurrentPrice(parsedRequest.getString("symbol"));
                break;
        }

        sendResponse(exchange, response);
    }

    private static void sendResponse(HttpExchange httpExchange, Response response) throws IOException {
        int statusCode;

        statusCode = 200;
        // Stringifiy responsetypes body
        String responseToSendString = response.generateResponse().toString();
        // Send responsetypes back to client
        byte[] responseBytes = responseToSendString.getBytes("UTF-8");
        // Set Cors headers
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers",
                "*,X-Requested-With,Content-Type,X-Device-License-Key,X-User-Agent");
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            httpExchange.sendResponseHeaders(204, -1);
            httpExchange.getResponseBody().close();
            return;
        }
        httpExchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(responseBytes);
        os.close();

    }

    private static JSONObject parseRequestBody(HttpExchange httpExchange) {
        try {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String inputLine;
            StringBuffer requestString = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                requestString.append(inputLine);
            }
            br.close();
            if (requestString.toString().isEmpty()) {
                return new JSONObject();
            } else {
                return new JSONObject(requestString.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
