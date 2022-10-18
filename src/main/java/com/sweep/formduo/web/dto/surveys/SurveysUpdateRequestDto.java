package com.sweep.formduo.web.dto.surveys;

import com.sweep.formduo.domain.surveys.Surveys;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link Surveys} entity
 */
@Getter
@NoArgsConstructor
public class SurveysUpdateRequestDto {

    private  String svyTitle;
    private  String svyIntro;
    private List<Map<String, Object>> svyContent;
    private  Instant svyStartDt;
    private  Instant svyEndDt;
    private  String svyEndMsg;
    private  Integer svyRespMax;
    private  Integer svyRespCount;

    private  Instant updDt;

    // 클라이언트가 요청할때 필요한 정보들입니다~.

    @Builder
    public SurveysUpdateRequestDto(String svyTitle, String svyIntro, List<Map<String, Object>> svyContent,
                                   String svyEndMsg, Instant svyStartDt, Instant svyEndDt, int svyRespMax, int svyRespCount) {

        this.svyTitle = svyTitle;
        this.svyIntro = svyIntro;
        this.svyContent = svyContent;
        this.svyEndMsg = svyEndMsg;
        this.svyStartDt = svyStartDt;
        this.svyEndDt = svyEndDt;
        this.svyRespMax = svyRespMax;
        this.svyRespCount = svyRespCount;
        this.updDt = Instant.now();
    }
}
