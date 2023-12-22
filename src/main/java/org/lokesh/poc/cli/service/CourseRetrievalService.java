package org.lokesh.poc.cli.service;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CourseRetrievalService {
    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/sander-mak/all-content";
    private static final HttpClient client = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public String getCourseFor(String authorId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PS_URI.formatted(authorId)))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return switch (response.statusCode()) {
                case 200 -> response.body();
                case 404 -> "";
                default -> throw new RuntimeException("Plural sight API call failed with status code " + response.statusCode());
            };
        } catch ( IOException | InterruptedException e) {
            throw new RuntimeException("Could not call pluralsight ", e);
        }
    }
}
