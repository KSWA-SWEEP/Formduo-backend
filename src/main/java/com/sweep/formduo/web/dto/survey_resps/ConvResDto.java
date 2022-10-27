package com.sweep.formduo.web.dto.survey_resps;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ConvResDto {

    private String emotion;
    private Integer value;

    @Builder
    public ConvResDto(String emotion, Integer value){
        this.emotion = emotion;
        this.value = value;
    }
}
