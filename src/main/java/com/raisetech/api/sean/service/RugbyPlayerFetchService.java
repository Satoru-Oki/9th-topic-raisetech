package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RugbyPlayerFetchService {

    @Autowired
    private RestTemplate restTemplate;

    public List<RugbyPlayer> getDataFromExternalApi() {
        String url = "https://api.sportradar.us/rugby-union/trial/v3/ja/competitors/sr:competitor:7955/profile.json?api_key=7xjkp8q8tqw3txwj4u8h7k9h";

        ResponseEntity<TeamInfo> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeamInfo>() {
                });

        return response.getBody().getPlayers();
    }
}
