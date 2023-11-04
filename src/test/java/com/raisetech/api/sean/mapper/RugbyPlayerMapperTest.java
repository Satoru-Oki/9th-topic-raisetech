package com.raisetech.api.sean.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.raisetech.api.sean.entity.RugbyPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RugbyPlayerMapperTest {

    @Autowired
    RugbyPlayerMapper rugbyPlayerMapper;

    @Test
    @ExpectedDataSet(value = "datasets/insert_insertPlayerData.yml")
    @Transactional
    void insertPlayerData_データベースにデータを登録できること() {
        rugbyPlayerMapper.insertPlayerData(new RugbyPlayer("1", "Kenki, Fukuoka", 175, 81, "WTB"));
    }

    @Test
    @DataSet(value = "datasets/rugbyPlayers.yml")
    @Transactional
    void findPlayerById_指定したIDを持つラグビー選手のデータが取得できること() {
        assertThat(rugbyPlayerMapper.findPlayerById("2")).contains(new RugbyPlayer("2", "Seiji, Hirao", 178, 78, "SO"));
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    @DataSet("datasets/rugbyPlayers.yml")
    @Transactional
    void findPlayersByReference_nullを含んだ身長体重ポジションの組み合わせで検索ができること(Integer height, Integer weight, String rugbyPosition) {
        List<RugbyPlayer> players = rugbyPlayerMapper.findPlayersByReference(height, weight, rugbyPosition);

        if (height != null) {
            players.forEach(player -> assertTrue(player.getHeight() >= height));
        }
        if (weight != null) {
            players.forEach(player -> assertTrue(player.getWeight() >= weight));
        }
        if (rugbyPosition != null) {
            players.forEach(player -> assertEquals(rugbyPosition, player.getRugbyPosition()));
        }
    }

    static Stream<Arguments> provideParameters() {
        Integer[] heights = {null, 177};
        Integer[] weights = {null, 83};
        String[] rugbyPositions = {null, "WTB"};

        return Arrays.stream(heights)
                .flatMap(height -> Arrays.stream(weights)
                        .flatMap(weight -> Arrays.stream(rugbyPositions)
                                .map(rugby_position -> Arguments.of(height, weight, rugby_position))
                        )
                );
    }

    @ParameterizedTest
    @MethodSource("playerSearchCriteria")
    @DataSet("datasets/rugbyPlayers.yml")
    @Transactional
    void findPlayersByReference_nullを含んだ身長体重ポジションの組み合わせで検索ができること(Integer height, Integer
            weight, String rugbyPosition, List<RugbyPlayer> expectedPlayers) {

        List<RugbyPlayer> players = rugbyPlayerMapper.findPlayersByReference(height, weight, rugbyPosition);

        assertEquals(expectedPlayers, players);
    }

    static Stream<Arguments> playerSearchCriteria() {
        return Stream.of(
                Arguments.of(null, null, null, List.of(new RugbyPlayer("1", "Kenki, Fukuoka", 175, 81, "WTB"), new RugbyPlayer("2", "Seiji, Hirao", 178, 78, "SO"),
                        new RugbyPlayer("3", "Gunter, Ben", 192, 115, "FL"))),
                Arguments.of(177, 100, "WTB", Collections.emptyList()),
                Arguments.of(176, null, null, List.of(new RugbyPlayer("2", "Seiji, Hirao", 178, 78, "SO"), new RugbyPlayer("3", "Gunter, Ben", 192, 115, "FL"))),
                Arguments.of(null, 100, null, List.of(new RugbyPlayer("3", "Gunter, Ben", 192, 115, "FL"))),
                Arguments.of(null, null, "WTB", List.of(new RugbyPlayer("1", "Kenki, Fukuoka", 175, 81, "WTB"))),
                Arguments.of(178, 78, null, List.of(new RugbyPlayer("2", "Seiji, Hirao", 178, 78, "SO"), new RugbyPlayer("3", "Gunter, Ben", 192, 115, "FL"))),
                Arguments.of(192, null, "FL", List.of(new RugbyPlayer("3", "Gunter, Ben", 192, 115, "FL"))),
                Arguments.of(null, 81, "WTB", List.of(new RugbyPlayer("1", "Kenki, Fukuoka", 175, 81, "WTB")))
        );
    }
}
