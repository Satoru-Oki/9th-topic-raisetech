package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//このクラスがないとテーブルが空のままとなる
@Component
public class RugbyPlayerInitializer {

    @Autowired
    private RugbyPlayerFetchService rugbyPlayerFetchService;

    @Autowired
    private RugbyPlayerInfoService rugbyPlayerInfoService;

    @PostConstruct
    public void init() {
        List<RugbyPlayer> rugbyPlayersList = rugbyPlayerFetchService.getDataFromExternalApi();
        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayersList);
    }
}
