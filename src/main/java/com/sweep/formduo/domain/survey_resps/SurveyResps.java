package com.sweep.formduo.domain.survey_resps;

import com.sweep.formduo.domain.surveys.Surveys;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
@Table(name = "TB_SVY_RESP")
public class SurveyResps {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SVY_RESP_ID", nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = Surveys.class)
    @JoinColumn(name = "SVY_ID")
    private Surveys survey;

    @Column(name = "SVY_RESP_DT")
    private Instant svyRespDt;

    @Type(type="json")
    @Column(name = "SVY_RESP_CONTENT", nullable = false, columnDefinition = "json")
    private List<Map<String, Object>> svyRespContent;

    @Builder
    public SurveyResps(Surveys survey, Instant svyRespDt,
                       List<Map<String, Object>> svyRespContent)
    {
        this.survey = survey;
        this.svyRespDt = svyRespDt;
        this.svyRespContent = svyRespContent;
    }


}