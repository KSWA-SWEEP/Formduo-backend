package com.sweep.formduo.web.dto.qbox;



import com.sweep.formduo.domain.qbox.Qbox;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link Qbox} entity
 */
@Getter
@NoArgsConstructor
@Data
public class QboxRequestDto {

    private String qTitle;
    private String qInfo;
    private String name;
    private String qImage = "";
    private String qVideo = "";
    private String qMulti = "";
    private List<Map<String, Object>> qContents;
    private char contentYn;

    // 클라이언트가 요청할때 필요한 정보들입니다~.

    @Builder
    public QboxRequestDto(String qTitle, String qInfo, String name, String qImage,
                          String qVideo, String qMulti, List<Map<String, Object>> qContents,
                          Character contentYn){

        this.qTitle = qTitle;
        this.qInfo = qInfo;
        this.name = name;
        this.qImage = qImage;
        this.qVideo = qVideo;
        this.qMulti = qMulti;
        this.qContents = qContents;
        this.contentYn = contentYn;
    }

    public Qbox toEntity() {
        return Qbox.builder()
                .qTitle(qTitle)
                .qInfo(qInfo)
                .name(name)
                .qImage(qImage)
                .qVideo(qVideo)
                .qMulti(qMulti)
                .qContents(qContents)
                .contentYn(contentYn)
                .delYn('N')
                .regDt(Instant.now())
                .updDt(Instant.now())
                .build();
    }
}