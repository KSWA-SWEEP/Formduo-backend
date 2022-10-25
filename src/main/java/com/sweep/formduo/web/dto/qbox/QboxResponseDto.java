package com.sweep.formduo.web.dto.qbox;

import com.sweep.formduo.domain.qbox.Qbox;
import com.sweep.formduo.domain.surveys.Surveys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link Surveys} entity
 */
@Getter
@NoArgsConstructor
public class QboxResponseDto {

    private int qId;
    private String qTitle;

    private String qInfo;

    private String name;

    private String qImage;

    private String qVideo;

    private String qMulti;

    private List<Map<String, Object>> qContents;

    private Character contentYn;
    private Character delYn;
    private Instant updDt;

    // 클라이언트가 요청했을 때 보여질 애들을 정합시다~.

    public QboxResponseDto(Qbox entity){
        this.qId = entity.getQId();
        this.qTitle = entity.getQTitle();
        this.qInfo = entity.getQInfo();
        this.name = entity.getName();
        this.qImage = entity.getQImage();
        this.qVideo = entity.getQVideo();
        this.qMulti = entity.getQMulti();
        this.qContents = entity.getQContents();
        this.contentYn = entity.getContentYn();
        this.updDt = entity.getUpdDt();
    }
    

}