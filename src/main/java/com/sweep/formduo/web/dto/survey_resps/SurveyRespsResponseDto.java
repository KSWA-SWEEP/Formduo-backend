package com.sweep.formduo.web.dto.survey_resps;

import com.sweep.formduo.domain.survey_resps.SurveyResps;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link SurveyResps} entity
 */
@Getter
@NoArgsConstructor
public class SurveyRespsResponseDto {
    private Integer id;
    private Integer svyId;
    private Integer svyRespsCount;

    private Integer svyRespsMax;
    private Instant svyRespDt;
    private List<Map<String, Object>> svyRespContent;


    public SurveyRespsResponseDto(SurveyResps entity){
        this.id = entity.getId();
        this.svyId = entity.getSurvey().getId();
        this.svyRespsCount = entity.getSurvey().getSvyRespCount();
        this.svyRespsMax = entity.getSurvey().getSvyRespMax();
        this.svyRespDt = entity.getSvyRespDt();
        this.svyRespContent = entity.getSvyRespContent();
    }
}