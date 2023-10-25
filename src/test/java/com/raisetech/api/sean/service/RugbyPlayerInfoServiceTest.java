package com.raisetech.api.sean.service;

import com.raisetech.api.sean.controller.PlayerDataResponse;
import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RugbyPlayerInfoServiceTest {

    @InjectMocks
    public RugbyPlayerInfoService rugbyPlayerInfoService;

    @Mock
    private RugbyPlayerMapper rugbyPlayerMapper;

    @Test
    public void テーブルにデータが存在しないときにデータを登録できること() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        List<RugbyPlayer> rugbyPlayerList = List.of(rugbyPlayer);
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("1");

        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayerList);

        verify(rugbyPlayerMapper, times(1)).insertPlayerData(rugbyPlayer);
    }

    @Test
    public void テーブルに同じIDが存在するときはデータが登録されないこと() {
        RugbyPlayer existingPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        List<RugbyPlayer> rugbyPlayerList = List.of(existingPlayer);
        doReturn(Optional.of(existingPlayer)).when(rugbyPlayerMapper).findPlayerById("1");

        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayerList);

        verify(rugbyPlayerMapper, never()).insertPlayerData(existingPlayer);
    }

    @Test
    public void テーブルからデータを検索し値があればレスポンスを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");

        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, "WTB");

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, "WTB");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void 検索したデータが存在しないときに例外を返すこと() {

        doReturn(List.of()).when(rugbyPlayerMapper).findPlayersByReference(175, 85, "WTB");

        assertThrows(PlayerNotFoundException.class, () -> {
            rugbyPlayerInfoService.findPlayersByReference(175, 85, "WTB");
        });
    }

    @Test
    public void 身長で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, null, null);

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(175, null, null);

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void 体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, null);

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, null);

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void ポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, null, "WTB");

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(null, null, "WTB");

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void 身長と体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, null);

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, null);

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void 身長とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, null, "WTB");

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(175, null, "WTB");

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void 体重とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, "WTB");

        ResponseEntity<List<PlayerDataResponse>> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, "WTB");

        assertTrue(response.getBody() instanceof List);
        assertEquals(200, response.getStatusCodeValue());
    }
}
