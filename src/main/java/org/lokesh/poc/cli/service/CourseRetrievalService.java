package org.lokesh.poc.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetrievalService {
    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/sander-mak/all-content";
    private static final HttpClient client = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<PluralsightCourse> getCourseFor(String authorId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PS_URI.formatted(authorId)))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return switch (response.statusCode()) {
                case 200 -> toPluralsightCourse(response);
                case 404 -> List.of();
                default -> throw new RuntimeException("Plural sight API call failed with status code " + response.statusCode());
            };
        } catch ( IOException | InterruptedException e) {
            throw new RuntimeException("Could not call pluralsight ", e);
        }
    }
    private List<PluralsightCourse> toPluralsightCourse(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, PluralsightCourse.class);
        return OBJECT_MAPPER.readValue(response.body(), returnType);
    }
}
