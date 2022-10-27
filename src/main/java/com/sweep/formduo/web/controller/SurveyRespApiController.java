package com.sweep.formduo.web.controller;


import com.sweep.formduo.service.surveys.SurveyRespService;
import com.sweep.formduo.util.RequestUtil;
import com.sweep.formduo.web.dto.survey_resps.ConvReqDto;
import com.sweep.formduo.web.dto.survey_resps.ConvResDto;
import com.sweep.formduo.web.dto.survey_resps.SurveyRespsRequestDto;
import com.sweep.formduo.web.dto.survey_resps.SurveyRespsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "설문 응답", description = "설문 응답 관련 api 입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class SurveyRespApiController {

    private final SurveyRespService surveyRespService;


    @Operation(summary = "설문 응답 생성 요청", description = "설문에 대한 응답이 생성됩니다.")
    @PostMapping("/resp")
    public int save(@RequestBody SurveyRespsRequestDto requestDto) {
        return surveyRespService.save(requestDto);
    }

    @Operation(summary = "설문 응답 조회 요청", description = "설문이 대한 특정 응답이 조회됩니다.")
    @GetMapping("/surveys/resp/{id}")
    public SurveyRespsResponseDto findSurveyRespById (@PathVariable Integer id) {
        return surveyRespService.findById(id);
    }

    @Operation(summary = "설문 전체 응답 요청!!", description = "설문에 대한 전체응답정보를 요청합니다.")
    @GetMapping("/surveys/{svyId}/resp")
    public List<SurveyRespsResponseDto> findAllRepsById (@PathVariable Integer svyId) {
        return surveyRespService.findAll(svyId);
    }

    @Operation(summary = "설문 발화 분석", description = "답변에 대한 카카오 발화분석 api 요청")
    @PostMapping("/conv")
    public ConvResDto conv(@RequestBody ConvReqDto convDto) throws ParseException {
        return RequestUtil.restRequest(convDto.getMsg());
    }

}
