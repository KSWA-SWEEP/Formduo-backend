package com.sweep.formduo.web.dto.surveys;

import com.sweep.formduo.domain.qbox.Qbox;
import com.sweep.formduo.domain.surveys.Surveys;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link Surveys} entity
 */
@Getter
@NoArgsConstructor
public class SurveysResponseDto  {

    private  Integer id;
    private  String regUser;
    private  String svySt;
    private  String svyTitle;
    private  String svyIntro;
    private List<Map<String, Object>> svyContent;
    private  Instant svyStartDt;
    private  Instant svyEndDt;
    private  Character delYn;
    private  String svyEndMsg;
    private  Instant svyRegDt;

    private Integer svyRespMax;
    private Integer svyRespCount;
    private String svyType;

    // 클라이언트가 요청했을 때 보여질 애들을 정합시다~.

    public SurveysResponseDto(Surveys entity){
//        System.out.println(entity.getSvyContent());
        this.id = entity.getId();
        this.regUser = entity.getEmail();
        this.svySt = entity.getSvySt();
        this.svyTitle = entity.getSvyTitle();
        this.svyIntro = entity.getSvyIntro();
        this.svyContent = entity.getSvyContent();
        this.delYn = entity.getDelYn();
        this.svyEndMsg = entity.getSvyEndMsg();
        this.svyStartDt = entity.getSvyStartDt();
        this.svyEndDt = entity.getSvyEndDt();
        this.svyRespMax = entity.getSvyRespMax();
        this.svyRespCount = entity.getSvyRespCount();
        this.svyRegDt = entity.getRegDt();
        this.svyType = entity.getSvyType();
    }


//    public Surveys toEntity() {
//        return Surveys.builder()
//                .regUser(regUser)
//                .svyTitle(svyTitle)
//                .svyIntro(svyIntro)
//                .svyContent(svyContent)
//                .svyEndMsg(svyEndMsg)
//                .delYn(delYn)
//                .build();
//    }
}