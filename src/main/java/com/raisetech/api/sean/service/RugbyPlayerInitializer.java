package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

//このクラスがないとテーブルが空のままとなる
@Component
public class RugbyPlayerInitializer {

    private final RugbyPlayerFetchService rugbyPlayerFetchService;

    private final RugbyPlayerInfoService rugbyPlayerInfoService;

    public RugbyPlayerInitializer(RugbyPlayerFetchService rugbyPlayerFetchService, RugbyPlayerInfoService rugbyPlayerInfoService) {
        this.rugbyPlayerFetchService = rugbyPlayerFetchService;
        this.rugbyPlayerInfoService = rugbyPlayerInfoService;
    }

    @PostConstruct
    public void init() {
        List<RugbyPlayer> rugbyPlayersList = rugbyPlayerFetchService.getDataFromExternalApi();
        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayersList);
    }
}
