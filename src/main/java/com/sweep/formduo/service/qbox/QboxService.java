package com.sweep.formduo.service.qbox;

import com.sweep.formduo.domain.members.Members;
import com.sweep.formduo.domain.posts.Posts;
import com.sweep.formduo.domain.qbox.Qbox;
import com.sweep.formduo.domain.qbox.QboxRepository;
import com.sweep.formduo.util.SecurityUtil;
import com.sweep.formduo.web.dto.posts.PostsResponseDto;
import com.sweep.formduo.web.dto.qbox.QboxRequestDto;
import com.sweep.formduo.web.dto.qbox.QboxResponseDto;
import com.sweep.formduo.web.dto.surveys.SurveysRequestDto;
import com.sweep.formduo.web.dto.surveys.SurveysResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class QboxService {

    private final QboxRepository qboxRepository;


    @Transactional
    public Integer save(QboxRequestDto requestDto) {
        return qboxRepository.save(requestDto.toEntity()).getQId();
    }

    @Transactional
    public QboxResponseDto findById(int id){
        Qbox entity = qboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 큐박스 컨텐츠가 없습니다. id ="+ id));

        return new QboxResponseDto(entity);}

    @Transactional
    public List<QboxResponseDto> findAll() {
        // 설문이 있는지 없는지 확인
        List<Qbox> list = qboxRepository.findAll();
        // 소팅 조건
//        Sort sort = Sort.by(Sort.Direction.DESC, "id", "regDt");
//        List<Surveys> list = surveysRepository.findAll(sort);

        return list.stream().map(QboxResponseDto::new).collect(Collectors.toList());
    }
}
