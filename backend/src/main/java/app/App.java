package app;

import com.sun.net.httpserver.HttpServer;
import config.Configuration;
import contract.PriceUpdater;
import http.PriceHttpHandler;

import service.PublisherService;
import service.UpdatePriceTask;

import java.net.InetSocketAddress;
import java.util.Timer;

public class App {
    static HttpServer server = null;
    private static boolean retry = false;

    public static void main(String[] args) {
        initServer();
        Configuration.loadSmartContract();
        initTimer();
        new PublisherService().listenToEvents();

    }

    private static void initServer() {
        System.out.println("In init server!");

        try {


            server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Create endpoints
            server.createContext("/price-history", new PriceHttpHandler("price-history"));
            server.createContext("/current-price", new PriceHttpHandler("current-price"));

            server.setExecutor(null);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            if(!retry) {
                retry = true;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            initServer();
        }

    }



    private static void initTimer() {
        Timer timer = new Timer("Timer");

        //long period = 1000l;
        long period = 1000l*60l;
        timer.scheduleAtFixedRate(new UpdatePriceTask(), 0l, period);
    }
}
