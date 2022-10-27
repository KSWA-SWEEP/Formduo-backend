package com.sweep.formduo.web.controller;

import com.sweep.formduo.web.dto.jwt.TokenDTO;
import com.sweep.formduo.web.dto.login.LoginReqDTO;
import com.sweep.formduo.web.dto.members.MemberReqDTO;
import com.sweep.formduo.web.dto.members.MemberRespDTO;
import com.sweep.formduo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthController 설명 : auth controller
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/02/14
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApisController {

    private final AuthService authService;
    @Value("${jwt.refresh-token-expire-time}")
    private long rtkLive;

    @GetMapping("/test")
    public String test(){
        return "OK";
    }

    @PostMapping("/signup")
    public MemberRespDTO signup(@RequestBody MemberReqDTO memberRequestDto) {
        log.debug("memberRequestDto = {}",memberRequestDto);
        return authService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public TokenDTO login(
            HttpServletResponse response,
            @RequestBody LoginReqDTO loginReqDTO) {
        return authService.login(loginReqDTO, response);
    }



//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(
//            HttpServletRequest request) {
//
//        return authService.logout(request);
//    }

    //로그아웃 만들기

//    @PostMapping("/login/kakao")
//    public ResponseEntity<?> createAuthenticationTokenByKakao(@RequestBody SocialLoginDto socialLoginDto) throws Exception {
//        //api 인증을 통해 얻어온 code값 받아오기
//        String username = authService.kakaoLogin(socialLoginDto.getToken());
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
//    }

    @PostMapping("/reissue")
    public TokenDTO reissue( HttpServletRequest request,
                            HttpServletResponse response
    ) {
        return authService.reissue(request, response);
    }
}
