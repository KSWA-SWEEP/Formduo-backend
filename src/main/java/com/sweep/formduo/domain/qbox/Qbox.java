package com.sweep.formduo.domain.qbox;

import com.sweep.formduo.domain.members.Members;
import com.sweep.formduo.domain.survey_resps.SurveyResps;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
//@TypeDef(name = "json", typeClass = JsonType.class)
@Table(name = "TB_QBOX")
public class Qbox {

//    private Instant current = Instant.now();

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "q_id", nullable = false)
    private Integer qId;

    @Column(name = "q_title", nullable = false)
    private String qTitle;

    @Column(name = "q_info", nullable = false)
    private String qInfo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "q_image")
    private String qImage;

    @Column(name = "q_video")
    private String qVideo;

    @Column(name = "q_multi")
    private String qMulti;

    @Type(type = "json")
    @Column(name = "q_contents",nullable = false, columnDefinition = "json")
    private List<Map<String, Object>> qContents;

    @Column(name = "CONTENT_YN")
    private Character contentYn;
    @Column(name = "DEL_YN")
    private Character delYn;

    @Column(name = "REG_DT")
    private Instant regDt;

    @Column(name = "UPD_DT")
    private Instant updDt;


    @Builder
    public Qbox(String qTitle, String qInfo, String name,
                String qImage, String qVideo, String qMulti, List<Map<String, Object>> qContents,
                Character contentYn, Character delYn, Instant regDt, Instant updDt){

        this.qTitle = qTitle;
        this.qInfo = qInfo;
        this.name = name;
        this.qImage = qImage;
        this.qVideo = qVideo;
        this.qMulti = qMulti;
        this.qContents = qContents;
        this.contentYn = contentYn;
        this.delYn = delYn;
        this.regDt = regDt;
        this.updDt = updDt;
    }

    public void update(String qTitle, String qInfo, String name,
                       String qImage, String qVideo, String qMulti, List<Map<String, Object>> qContents,
                       Character contentYn, Instant updDt){

        this.qTitle = qTitle;
        this.qInfo = qInfo;
        this.name = name;
        this.qImage = qImage;
        this.qVideo = qVideo;
        this.qMulti = qMulti;
        this.qContents = qContents;
        this.contentYn = contentYn;
        this.updDt = updDt;
    }

    public void remove() {
        this.delYn = 'Y';
    }

//    public void countUp(int svyRespCount) {
//        this.svyRespCount = svyRespCount;}

}