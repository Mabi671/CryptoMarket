package org.example;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class DownloadingData extends Thread{
    private final String symbol;
    DownloadingData(String symbol){
        this.symbol = symbol;
    }
    public void run() {
        String apiKey = "notNeeded";
        String baseUrl = "https://api.kucoin.com";
        String endpoint = "/api/v1/prices?currencies="+symbol;
        String url = baseUrl + endpoint;
        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
            HttpGet request = new HttpGet(url);
            request.addHeader("KC-API-KEY", apiKey);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                try{
                    String jsString = jsonObject.getAsJsonObject("data").toString();
                    int len = symbol.length();
                    if(jsString.length() > (len + 11)){
                        double value = Double.parseDouble(jsString.substring(5 + len, 11 + len));
                        Main.prices.put(symbol, value);
                    }
                }catch(NullPointerException e){
                    System.out.println(symbol + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        }

    }





