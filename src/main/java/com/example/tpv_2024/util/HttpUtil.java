package com.example.tpv_2024.util;


import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static String sendPostRequest(String url, String jsonInputString) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(new StringEntity(jsonInputString, StandardCharsets.UTF_8));
            request.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            }
        }
    }

    public static String sendGetRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendDeleteRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            httpClient.execute(httpDelete).close();
        }
    }
}
