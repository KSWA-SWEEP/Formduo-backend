package com.sweep.formduo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sweep.formduo.domain.surveys.Surveys;
import com.sweep.formduo.service.surveys.SurveyService;
import com.sweep.formduo.web.controller.SurveysApiController;
import com.sweep.formduo.web.dto.surveys.SurveysRequestDto;
import com.sweep.formduo.web.dto.surveys.SurveysResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.bytebuddy.dynamic.scaffold.MethodGraph;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SurveyServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    SurveyService surveyService;

    @Value("${test.token}")
    String token;

    @Test
    @DisplayName("설문 생성")
    public void 설문_생성() throws Exception {
        ObjectNode content = objectMapper.createObjectNode();
        content.put("svyTitle", "test");
        content.put("svyTitle", "test");
        content.put("svyIntro", "test");
        content.put("svyStartDt", "2022-10-28T08:30:09.045Z");
        content.put("svyEndDt", "2022-10-28T08:30:09.045Z");
        content.put("svyEndMsg", "test");
        content.put("svyRespMax", 0);
        content.put("svyRespCount", 0);
        content.put("svyType","basic");

        this.mockMvc.perform(
                post("/api/v1/surveys")
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content)))
                        .andExpect(status().isOk());
    }


    @Test
    public void 연결_확인() throws Exception {
        this.mockMvc.perform(get("/api/v1/auth/test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("나의 전체 설문 조회")
    public void 설문_조회() throws Exception {
        ObjectNode content = objectMapper.createObjectNode();
        this.mockMvc.perform(
                        get("/api/v1/surveys")
                                .header("Authorization", "Bearer "+token)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(content)))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("설문 업데이트")
    void 설문_업데이트() throws Exception {
        ObjectNode content = objectMapper.createObjectNode();
        content.put("svyTitle", "test");
        content.put("svyIntro", "test");
        content.put("svyStartDt", "2022-10-28T08:30:09.045Z");
        content.put("svyEndDt", "2022-10-28T08:30:09.045Z");
        content.put("svyEndMsg", "test");
        content.put("svyRespMax", 0);

        this.mockMvc.perform(
                        get("/api/v1/surveys/153")
                                .header("Authorization", "Bearer "+token)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(content)))
                )
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Type 별 설문 조회")
    void findByType() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", "basic");

        this.mockMvc.perform(
                        get("/api/v1/surveys/type")
                                .header("Authorization", "Bearer "+token)
                                .params(params)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(content)))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("설문 삭제")
    void remove() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("type", "basic");

        this.mockMvc.perform(
                        delete("/api/v1/surveys/150")
                                .header("Authorization", "Bearer "+token)
//                                .params(params)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(content)))
                )
                .andExpect(status().isOk());
    }
}