package com.raisetech.api.sean.service;//package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@ActiveProfiles("test")
public class RugbyPlayerFetchServiceTest {

    @Autowired
    private RugbyPlayerFetchService rugbyPlayerFetchService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void 外部APIへGETリクエストを送信し返されたJSONデータが正しくデシリアライズされること() {
        ReflectionTestUtils.setField(rugbyPlayerFetchService, "API_KEY", "MOCK_API_KEY");
        String apiUrl = "https://api.sportradar.us/rugby-union/trial/v3/ja/competitors/sr:competitor:7955/profile.json?api_key=MOCK_API_KEY";

        String responseBody = "{ \"team\": \"brave_blossoms\", \"players\": [ { \"id\": \"1\", \"name\": \"Sinki, Gen\", \"height\": 180, \"weight\": 95, \"type\": \"BR\" }, { \"id\": \"2\", \"name\": \"Kenki, Fukuoka\", \"height\": 170, \"weight\": 75, \"type\": \"WTB\" } ] }";

        mockServer.expect(requestTo(apiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<RugbyPlayer> players = rugbyPlayerFetchService.getDataFromExternalApi();
        List<RugbyPlayer> expected = List.of(new RugbyPlayer("1", "Sinki, Gen", 180, 95, "BR"), new RugbyPlayer("2", "Kenki, Fukuoka", 170, 75, "WTB"));
        assertEquals(expected, players);

        mockServer.verify();
    }
}
