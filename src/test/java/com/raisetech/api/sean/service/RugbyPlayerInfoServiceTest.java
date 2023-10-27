package com.raisetech.api.sean.service;

import com.raisetech.api.sean.controller.PlayerDataResponse;
import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RugbyPlayerInfoServiceTest {

    @InjectMocks
    public RugbyPlayerInfoService rugbyPlayerInfoService;

    @Mock
    private RugbyPlayerMapper rugbyPlayerMapper;

    @Test
    public void insertRugbyPlayers_テーブルにデータが存在しないときにデータを登録できること() {
        RugbyPlayer rugbyPlayer1 = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        RugbyPlayer rugbyPlayer2 = new RugbyPlayer("2", "Takeo, Ishizuka", 172, 85, "FL");
        RugbyPlayer rugbyPlayer3 = new RugbyPlayer("3", "Seiji, Hirao", 177, 80, "SO");

        List<RugbyPlayer> rugbyPlayerList = List.of(rugbyPlayer1, rugbyPlayer2, rugbyPlayer3);

        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("1");
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("2");
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("3");

        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayerList);

        verify(rugbyPlayerMapper, times(1)).insertPlayerData(rugbyPlayer1);
        verify(rugbyPlayerMapper, times(1)).insertPlayerData(rugbyPlayer2);
        verify(rugbyPlayerMapper, times(1)).insertPlayerData(rugbyPlayer3);
    }

    @Test
    public void insertRugbyPlayers_複数のデータを登録しようとしたときテーブルにすでに同じIDが存在する場合INSERTがスキップされること() {
        RugbyPlayer existingPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        RugbyPlayer newPlayer = new RugbyPlayer("2", "Takeo, Ishizuka", 172, 85, "FL");

        List<RugbyPlayer> rugbyPlayerList = List.of(existingPlayer, newPlayer);

        doReturn(Optional.of(existingPlayer)).when(rugbyPlayerMapper).findPlayerById("1");
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("2");

        rugbyPlayerInfoService.insertRugbyPlayers(rugbyPlayerList);

        verify(rugbyPlayerMapper, never()).insertPlayerData(existingPlayer);
        verify(rugbyPlayerMapper, times(1)).insertPlayerData(newPlayer);
    }

    @Test
    public void 身長と体重とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");

        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, "WTB");

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
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

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, null, null);

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }

    @Test
    public void 体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, null);

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, null);

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }

    @Test
    public void ポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, null, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, null, "WTB");

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }

    @Test
    public void 身長と体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, null);

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, null);

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }

    @Test
    public void 身長とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, null, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, null, "WTB");

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }

    @Test
    public void 体重とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, "WTB");

        assertEquals(1, response.size());
        PlayerDataResponse playerDataResponse = response.get(0);
        assertEquals("Kenki, Fukuoka", playerDataResponse.getName());
        assertEquals(175, playerDataResponse.getHeight());
        assertEquals(85, playerDataResponse.getWeight());
        assertEquals("WTB", playerDataResponse.getRugbyPosition());
    }
}
