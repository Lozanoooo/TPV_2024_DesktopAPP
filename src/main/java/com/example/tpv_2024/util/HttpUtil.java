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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String sendPostRequest(String url, String jsonInputString) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(new StringEntity(jsonInputString, StandardCharsets.UTF_8));
            request.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getEntity() != null) {
                    return new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    return "";
                }
            }
        } catch (IOException e) {
            logger.error("Error during POST request to {}: {}", url, e.getMessage());
            throw e;
        }
    }

    public static String sendGetRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    return "";
                }
            } catch (ParseException e) {
                logger.error("Error parsing GET response from {}: {}", url, e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            logger.error("Error during GET request to {}: {}", url, e.getMessage());
            throw e;
        }
    }

    public static void sendDeleteRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                // Ensure response is closed even if we don't use it
            }
        } catch (IOException e) {
            logger.error("Error during DELETE request to {}: {}", url, e.getMessage());
            throw e;
        }
    }
}
