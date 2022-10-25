package com.sweep.formduo.web.controller;

import com.sweep.formduo.service.qbox.QboxService;
import com.sweep.formduo.web.dto.qbox.QboxRequestDto;
import com.sweep.formduo.web.dto.qbox.QboxResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Q-BOX", description = "Q-BOX 관련 api 입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/v1/qbox")
@RestController
public class QboxApiController {

    private final QboxService qBoxService;

    @Operation(summary = "Q-BOX 생성 요청", description = "Q-BOX 생성을 요청합니다.")
    @PostMapping("")
    public int save(@RequestBody QboxRequestDto requestDto, HttpServletRequest httpServletRequest) {
        return qBoxService.save(requestDto);
    }

//    @Operation(summary = "Qbox 수정 요청", description = "Qbox id를 이용하여 내용 수정을 요청합니다.")
//    @PutMapping("/{id}")
//    public Long update(@PathVariable int id, @RequestBody PostsUpdateRequestDto requestDto){
//        return qBoxService.update(id, requestDto);
//    }

    @Operation(summary = "Q-BOX 조회 요청", description = "Q-BOX id를 이용하여 내용 조회를 요청합니다.")
    @GetMapping("/{id}")
    public QboxResponseDto findById (@PathVariable int id) {
        return qBoxService.findById(id);
    }

    @Operation(summary = "Q-BOX 전체 조회 요청", description = "Q-BOX 전체 내용 조회를 요청합니다.")
    @GetMapping("")
    public List<QboxResponseDto> findAll () {
        return qBoxService.findAll();
    }
}