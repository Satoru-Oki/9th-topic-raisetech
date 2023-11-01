package com.raisetech.api.sean.service;

import com.raisetech.api.sean.controller.PlayerDataResponse;
import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.form.PlayerCreateForm;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public void findPlayersByReference_身長と体重とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");

        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, "WTB");

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_検索したデータが存在しないときに例外を返すこと() {

        doReturn(List.of()).when(rugbyPlayerMapper).findPlayersByReference(175, 85, "WTB");

        assertThrows(PlayerNotFoundException.class, () -> {
            rugbyPlayerInfoService.findPlayersByReference(175, 85, "WTB");
        });
    }

    @Test
    public void findPlayersByReference_身長で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, null, null);

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, null, null);

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, null);

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, null);

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_ポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, null, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, null, "WTB");

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_身長と体重で検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, 85, null);

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, 85, null);

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_身長とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(175, null, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(175, null, "WTB");

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void findPlayersByReference_体重とポジションで検索したときにPlayerDataResponseのリストを返すこと() {
        RugbyPlayer rugbyPlayer = new RugbyPlayer("1", "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(List.of(rugbyPlayer)).when(rugbyPlayerMapper).findPlayersByReference(null, 85, "WTB");

        List<PlayerDataResponse> response = rugbyPlayerInfoService.findPlayersByReference(null, 85, "WTB");

        List<PlayerDataResponse> expected = List.of(new PlayerDataResponse("Kenki, Fukuoka", 175, 85, "WTB"));
        assertEquals(expected, response);
    }

    @Test
    public void createRugbyPlayer_RugbyCreateFormで取得した選手を登録できること() {
        PlayerCreateForm createForm = new PlayerCreateForm("Kenki, Fukuoka", 175, 85, "WTB");
        RugbyPlayer expectedRugbyPlayer = new RugbyPlayer("Kenki, Fukuoka", 175, 85, "WTB");
        expectedRugbyPlayer.setId(UUID.randomUUID().toString().substring(0, 8));
        lenient().doNothing().when(rugbyPlayerMapper).createRugbyPlayer(expectedRugbyPlayer);
        RugbyPlayer actual = rugbyPlayerInfoService.createRugbyPlayer(createForm);
        assertEquals(expectedRugbyPlayer.getName(), actual.getName());
        assertEquals(expectedRugbyPlayer.getHeight(), actual.getHeight());
        assertEquals(expectedRugbyPlayer.getWeight(), actual.getWeight());
        assertEquals(expectedRugbyPlayer.getRugbyPosition(), actual.getRugbyPosition());
    }

    @ParameterizedTest
    @MethodSource("updateRugbyPlayerParameters")
    public void updateRugbyPlayer_IDがある場合に他の属性がnullであっても正常に更新ができること(String playerId, String name, Integer height, Integer weight, String rugbyPosition) throws Exception {
        RugbyPlayer updatePlayer = new RugbyPlayer(playerId, "Kenki, Fukuoka", 175, 85, "WTB");
        doReturn(Optional.of(updatePlayer)).when(rugbyPlayerMapper).findPlayerById(playerId);

        rugbyPlayerInfoService.updateRugbyPlayer(playerId, name, height, weight, rugbyPosition);
        verify(rugbyPlayerMapper, times(1)).updateRugbyPlayer(playerId, name, height, weight, rugbyPosition);
    }

    private static Stream<Arguments> updateRugbyPlayerParameters() {
        String[] names = {null, "Takeo, Ishizuka"};
        Integer[] heights = {null, 171};
        Integer[] weights = {null, 80};
        String[] rugbyPositions = {null, "FL"};

        return Arrays.stream(names)
                .flatMap(name -> Arrays.stream(heights).flatMap(height ->
                        Arrays.stream(weights).flatMap(weight ->
                                Arrays.stream(rugbyPositions).map(rugbyPosition ->
                                        Arguments.of("1", name, height, weight, rugbyPosition)))));
    }

    @Test
    public void updateRugbyPlayer_指定したIDが存在しないとき例外を返すこと() {
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("999");

        assertThatThrownBy(() -> rugbyPlayerInfoService.updateRugbyPlayer("999", "Kenki, Fukuoka", 175, 85, "WTB"))
                .isInstanceOfSatisfying(PlayerNotFoundException.class, e -> {
                    assertThat(e.getMessage()).isEqualTo("当該IDを持つ選手は存在しません");
                });

        verify(rugbyPlayerMapper, times(1)).findPlayerById("999");
    }

    @Test
    public void updateRugbyPlayer_名前が空白のときに例外を返すこと() {

        doReturn(Optional.of(new RugbyPlayer())).when(rugbyPlayerMapper).findPlayerById("1");

        assertThatThrownBy(() -> rugbyPlayerInfoService.updateRugbyPlayer("1", " ", 175, 85, "WTB"))
                .isInstanceOfSatisfying(PlayerIllegalArgumentException.class, e -> {
                    assertThat(e.getMessage()).isEqualTo("空白の入力は許可されていません");
                });

        verify(rugbyPlayerMapper, times(1)).findPlayerById("1");
    }

    @Test
    public void updateRugbyPlayer_ポジションが空白だったときに例外を返すこと() {
        doReturn(Optional.of(new RugbyPlayer())).when(rugbyPlayerMapper).findPlayerById("1");

        assertThatThrownBy(() -> rugbyPlayerInfoService.updateRugbyPlayer("1", "Kenki, Fukuoka", 175, 85, " "))
                .isInstanceOfSatisfying(PlayerIllegalArgumentException.class, e -> {
                    assertThat(e.getMessage()).isEqualTo("空白の入力は許可されていません");
                });

        verify(rugbyPlayerMapper, times(1)).findPlayerById("1");
    }

    @Test
    public void deleteRugbyPlayer_IDがある場合に選手を削除できること() {
        doReturn(Optional.of(new RugbyPlayer())).when(rugbyPlayerMapper).findPlayerById("1");

        rugbyPlayerInfoService.deleteRugbyPlayer("1");

        verify(rugbyPlayerMapper, times(1)).findPlayerById("1");
        verify(rugbyPlayerMapper, times(1)).deleteRugbyPlayer("1");
    }

    @Test
    public void deleteRugbyPlayer_指定したIDが存在しないときに例外を返すこと() {
        doReturn(Optional.empty()).when(rugbyPlayerMapper).findPlayerById("999");

        assertThatThrownBy(() -> rugbyPlayerInfoService.deleteRugbyPlayer("999"))
                .isInstanceOfSatisfying(PlayerNotFoundException.class, e -> {
                    assertThat(e.getMessage()).isEqualTo("当該IDを持つ選手は存在しません");
                });

        verify(rugbyPlayerMapper, times(1)).findPlayerById("999");
        verify(rugbyPlayerMapper, never()).deleteRugbyPlayer("999");
    }
}
