package com.sweep.formduo.web.controller;


import com.sweep.formduo.service.users.UserService;
import com.sweep.formduo.web.dto.users.UsersRequestDto;
import com.sweep.formduo.web.dto.users.UsersResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(originPatterns = "http://localhost:3001")
@Tag(name = "유저", description = "유저 관련 api 입니다.")
@RequiredArgsConstructor
@RequestMapping("/v2/users")
@RestController
public class UsersApiController {

    private final UserService userService;

    @Operation(summary = "유저 생성 요청", description = "유저 정보가 생성됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @CrossOrigin(origins = "*")
    @PostMapping("")
    public int save(@RequestBody UsersRequestDto requestDto) {
        return userService.save(requestDto);
    }

//    @PostMapping("/api/v1/users/login")
//    public void login(@RequestBody String abc) {
//        System.out.println(abc);
//    };


    @Operation(summary = "유저 조회 요청", description = "유저 정보가 조회됩니다.")
    @GetMapping("{id}")
    public UsersResponseDto findSurveyById (@PathVariable Integer id) {
        return userService.findById(id);
    }

    @Operation(summary = "유저 삭제 요청", description = "유저 정보가 생성됩니다.")
    @DeleteMapping("{id}")
    public String remove(@PathVariable Integer id) {
        return userService.remove(id);
    }

}
