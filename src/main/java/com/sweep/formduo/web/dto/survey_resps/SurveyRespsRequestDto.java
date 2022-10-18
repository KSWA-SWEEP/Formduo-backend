package com.sweep.formduo.web.dto.survey_resps;

import com.sweep.formduo.domain.survey_resps.SurveyResps;
import com.sweep.formduo.domain.surveys.Surveys;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
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
public class SurveyRespsRequestDto{

    @Schema(description = "설문 아이디", defaultValue = "1")
    private Integer svyId;
    private List<Map<String, Object>> svyRespContent;

    @Builder
    public SurveyRespsRequestDto(int svyId, Instant svyRespDt, List<Map<String, Object>> svyRespContent){
        this.svyRespContent = svyRespContent;
    }

    public SurveyResps toEntity(Surveys survey) {
        return SurveyResps.builder()
                .survey(survey)
                .svyRespDt(Instant.now())
                .svyRespContent(svyRespContent)
                .build();
    }
}