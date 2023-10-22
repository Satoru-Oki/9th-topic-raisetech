package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RugbyPlayerFetchService {

    private final RestTemplate restTemplate;

    private final String API_URL = "https://api.sportradar.us/rugby-union/trial/v3/ja/competitors/sr:competitor:7955/profile.json";

    @Value("${rugby.api.key}")
    private String API_KEY;

    public RugbyPlayerFetchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RugbyPlayer> getDataFromExternalApi() {
        String url = String.format("%s?api_key=%s", API_URL, API_KEY);

        ResponseEntity<TeamInfo> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeamInfo>() {
                });

        return response.getBody().getPlayers();
    }
}
