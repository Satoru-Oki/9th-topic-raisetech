package com.raisetech.api.sean.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.raisetech.api.sean.service.RugbyPlayerFetchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RugbyPlayerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

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
    @DataSet(value = "datasets/rugbyPlayers.yml")
    @Transactional
    void クエリパラメータを指定しない場合すべての選手を取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/rugbyPlayers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                 {
                   "name":"Kenki, Fukuoka",
                   "height": 175,
                   "weight": 81,
                   "rugby_position": "WTB"
                 },
                 {
                   "name":"Seiji, Hirao",
                   "height": 178,
                   "weight": 78,
                   "rugby_position": "SO"
                 },
                 {
                   "name":"Gunter, Ben",
                   "height": 192,
                   "weight": 115,
                   "rugby_position": "FL"
                 }
                ]
                 """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/rugbyPlayers.yml")
    @Transactional
    void height_weight_rugbyPositionで検索したときに該当する選手が取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/rugbyPlayers?height=178&weight=78&rugbyPosition=SO"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                 {
                   "name":"Seiji, Hirao",
                   "height": 178,
                   "weight": 78,
                   "rugby_position": "SO"
                 }
                ]
                 """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/rugbyPlayers.yml")
    @Transactional
    void 検索した選手が存在しない時に例外を出力すること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.get("/rugbyPlayers?height=200"))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                  "message": "条件に該当する選手は存在しないか、条件の指定が誤っています"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("message", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "rugbyPlayers.yml")
    @ExpectedDataSet(value = "insert_rugbyplayer.yml", ignoreCols = "id")
    @Transactional
    void 選手が登録できること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/rugbyPlayers")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                          "name": "Takeo, Ishizuka",
                                          "height": 172,
                                          "weight": 85,
                                          "rugby_position": "FL"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                  "message": "選手が登録されました"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("message", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "rugbyPlayers.yml")
    @Transactional
    void POSTの際nullの項目を登録したときにエラーメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/rugbyPlayers")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                         {
                                           "name": null,
                                           "height": 172,
                                           "weight": 85,
                                           "rugby_position": "FL"
                                          }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @DataSet(value = "rugbyPlayers.yml")
    @Transactional
    void POSTの際に身長が100から300の間に無い数値を登録したときにエラーメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/rugbyPlayers")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "Satoru, Oki",
                                            "height": 900,
                                            "weight": 80,
                                            "rugby_position": "FL"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message": [
                        "身長は100から300の間で登録してください"
                    ]
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("message", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "rugbyPlayers.yml")
    @Transactional
    void POSTの際に体重が10から300の間に無い数値を登録したときにエラーメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/rugbyPlayers")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "Satoru, Oki",
                                            "height": 172,
                                            "weight": 9,
                                            "rugby_position": "FL"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message": [
                        "体重は10から300の間で登録してください"
                    ]
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("message", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "rugbyPlayers.yml")
    @Transactional
    void POSTの際にname_rugbypositionに空文字_空白を入力した場合にエラーメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/rugbyPlayers")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "Satoru, Oki",
                                            "height": 172,
                                            "weight": 85,
                                            "rugby_position": ""
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message": [
                        "空白は許可されていません"
                    ]
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("message", ((o1, o2) -> true))));
    }
}
