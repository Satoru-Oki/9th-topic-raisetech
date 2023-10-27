package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RugbyPlayerInitializerTest {

    @Mock
    private RugbyPlayerFetchService rugbyPlayerFetchService;

    @Mock
    private RugbyPlayerInfoService rugbyPlayerInfoService;

    @InjectMocks
    private RugbyPlayerInitializer rugbyPlayerInitializer;

    @Test
    public void アプリ起動時にFetchServiceで取得した外部APIのデータをInfoServiceを使用してデータベースに投入できること() {
        RugbyPlayer player = new RugbyPlayer("1", "Sinki, Gen", 180, 80, "BR");
        List<RugbyPlayer> playersList = List.of(player);
        doReturn(playersList).when(rugbyPlayerFetchService).getDataFromExternalApi();

        rugbyPlayerInitializer.init();

        verify(rugbyPlayerFetchService, times(1)).getDataFromExternalApi();
        verify(rugbyPlayerInfoService, times(1)).insertRugbyPlayers(playersList);
    }
}
