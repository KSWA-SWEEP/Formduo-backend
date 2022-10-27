package com.sweep.formduo.service.surveys;

import com.sweep.formduo.service.members.MemberService;
import com.sweep.formduo.domain.members.MemberRepository;
import com.sweep.formduo.domain.members.Members;
import com.sweep.formduo.domain.surveys.Surveys;
import com.sweep.formduo.domain.surveys.SurveysRepository;
import com.sweep.formduo.util.SecurityUtil;
import com.sweep.formduo.web.dto.surveys.SurveysRequestDto;
import com.sweep.formduo.web.dto.surveys.SurveysResponseDto;
import com.sweep.formduo.web.dto.surveys.SurveysUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyService {
    private final SurveysRepository surveysRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    public Integer save(SurveysRequestDto requestDto) {
        Members members = memberRepository.findByEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. " + SecurityUtil.getCurrentMemberEmail()));

        return surveysRepository.save(requestDto.toEntity(members)).getId();
    }

    @Transactional
    public Integer update(int id, SurveysUpdateRequestDto requestDto){
        Surveys surveys = surveysRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id =" + id));

        surveys.update(requestDto.getSvyTitle(),
                requestDto.getSvyIntro(),
                requestDto.getSvyContent(),
                requestDto.getSvyStartDt(),
                requestDto.getSvyEndDt(),
                requestDto.getUpdDt(),
                requestDto.getSvyEndMsg(),
                requestDto.getSvyRespCount(),
                requestDto.getSvyRespMax());
        return id;
    }

    public SurveysResponseDto findById(int id){
        Surveys entity = surveysRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 설문이 없습니다. id ="+ id));

        System.out.println(entity);
        return new SurveysResponseDto(entity);}

    @Transactional
    public String remove(int id){
        Surveys entity = surveysRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 설문이 없습니다. id ="+ id));

//        surveysRepository.deleteById(id);
        entity.remove();
        return entity.getSvyTitle();}


    public List<SurveysResponseDto> findAll(HttpServletRequest request) {
        // 설문이 있는지 없는지 확인
        List<Surveys> list = surveysRepository.findAllByEmail(memberService.getMyInfo(request).getEmail());
        // 소팅 조건
//        Sort sort = Sort.by(Sort.Direction.DESC, "id", "regDt");
//        List<Surveys> list = surveysRepository.findAll(sort);

        return list.stream().map(SurveysResponseDto::new).collect(Collectors.toList());
    }
}
