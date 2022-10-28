package com.sweep.formduo.service.surveys;

import com.sweep.formduo.domain.survey_resps.SurveyResps;
import com.sweep.formduo.domain.survey_resps.SurveyRespsRepository;
import com.sweep.formduo.domain.surveys.Surveys;
import com.sweep.formduo.domain.surveys.SurveysRepository;
import com.sweep.formduo.web.dto.survey_resps.SurveyRespsRequestDto;
import com.sweep.formduo.web.dto.survey_resps.SurveyRespsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SurveyRespService {
    private final SurveysRepository surveysRepository;
    private final SurveyRespsRepository surveyRespsRepository;



    @Transactional
    public Integer save(SurveyRespsRequestDto requestDto) {

        // 설문이 있는지 없는지 확인
        Surveys surveys = surveysRepository.findById(requestDto.getSvyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 설문이 없습니다. id =" + requestDto.getSvyId()));

        // 기간이 지났는지 확인
        if(surveys.getSvyEndDt().isBefore(Instant.now()))
        {
           throw new IllegalArgumentException("설문 기간이 지났습니다.");
        }
        // 응답수가 최대 수를 넘었는지 확인
        if(surveys.getSvyRespMax() < surveys.getSvyRespCount()+1)
        {
            throw new IllegalArgumentException("설문 응답자 수가 초과되었습니다.");
        }
        surveys.countUp(surveys.getSvyRespCount()+1);
        return surveyRespsRepository.save(requestDto.toEntity(surveys)).getId();
    }

    public SurveyRespsResponseDto findById(int id){
        SurveyResps entity = surveyRespsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 응답이 없습니다. id ="+ id));
        return new SurveyRespsResponseDto(entity);}

    public List<SurveyRespsResponseDto> findAll(int svyId) {
        // 설문이 있는지 없는지 확인
        Surveys surveys = surveysRepository.findById(svyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 설문이 없습니다. 설문 id =" + svyId));
//        Sort sort = Sort.by(Sort.Direction.DESC, "id", "svyRespDt");

        List<SurveyResps> list = surveys.getSurveyResps();

        return list.stream().map(SurveyRespsResponseDto::new).collect(Collectors.toList());
    }
}
