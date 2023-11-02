package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class StockData extends Thread {
    private final String symbol;
    StockData(String symbol){
        this.symbol = symbol;
    }
    public void run(){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = "https://finnhub.io/api/v1/quote?symbol="+symbol+"kot";
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        String responseBody = EntityUtils.toString(entity);
                        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                        try{
                            double value = jsonObject.get("c").getAsDouble();
                            Main.stockPrices.put(symbol, value);
                        }catch(NullPointerException e){
                            System.out.println(symbol);
                        }
                    }
                } else {
                    System.out.println("Error: " + response.getStatusLine().getStatusCode());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
