package com.sweep.formduo.service.auth;

import com.sweep.formduo.domain.auth.MemberAuth;
import com.sweep.formduo.domain.auth.Authority;
import com.sweep.formduo.domain.auth.AuthorityRepository;
import com.sweep.formduo.domain.members.Members;
import com.sweep.formduo.domain.members.MemberRepository;
import com.sweep.formduo.domain.token.RefreshToken;
import com.sweep.formduo.domain.token.RefreshTokenRepository;
import com.sweep.formduo.util.HeaderUtil;
import com.sweep.formduo.service.members.CustomUserDetailsService;
import com.sweep.formduo.util.CookieUtil;
import com.sweep.formduo.web.dto.jwt.TokenDTO;
import com.sweep.formduo.web.dto.jwt.TokenReqDTO;
import com.sweep.formduo.web.dto.login.LoginReqDTO;
import com.sweep.formduo.web.dto.members.MemberEmailDto;
import com.sweep.formduo.web.dto.members.MemberReqDTO;
import com.sweep.formduo.web.dto.members.MemberRespDTO;
import com.sweep.formduo.util.exceptionhandler.AuthorityExceptionType;
import com.sweep.formduo.util.exceptionhandler.BizException;
import com.sweep.formduo.util.exceptionhandler.JwtExceptionType;
import com.sweep.formduo.util.exceptionhandler.MemberExceptionType;
import com.sweep.formduo.jwt.CustomEmailPasswordAuthToken;
import com.sweep.formduo.jwt.TokenProvider;
import com.sweep.formduo.web.dto.members.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisService redisService;
    @Value("${jwt.refresh-token-expire-time}")
    private long rtkLive;

    @Value("${jwt.access-token-expire-time}")
    private long accExpTime;


    @Transactional
    public MemberRespDTO signup(MemberReqDTO memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new BizException(MemberExceptionType.DUPLICATE_USER);
        }

        // DB ?????? ROLE_USER??? ????????? ???????????? ????????????.
        Authority authority = authorityRepository
                .findByAuthorityName(MemberAuth.ROLE_USER).orElseThrow(()->new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority);


        Members members = memberRequestDto.toMember(passwordEncoder,set);
        log.debug("member = {}", members);
        return MemberRespDTO.of(memberRepository.save(members));
    }

    @Transactional
    public TokenDTO login(LoginReqDTO loginReqDTO, HttpServletResponse response) {
        CustomEmailPasswordAuthToken customEmailPasswordAuthToken = new CustomEmailPasswordAuthToken(loginReqDTO.getEmail(),loginReqDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);
        String email = authenticate.getName();
        Members members = customUserDetailsService.getMember(email);
//        System.out.println(email + members.toString());

        String accessToken = tokenProvider.createAccessToken(email, members.getAuthorities());
        String refreshToken = tokenProvider.createRefreshToken(email, members.getAuthorities());

//        System.out.println(accessToken);
//        System.out.println(refreshToken);

        //redis??? refresh token ??????
        redisService.setValues(email, refreshToken, Duration.ofMillis(rtkLive));

        int cookieMaxAge = (int) rtkLive / 60;

//        CookieUtil.addCookie(response, "access_token", accessToken, cookieMaxAge);
        CookieUtil.addCookie(response, "refresh_token", refreshToken, cookieMaxAge);

        // ????????? ?????? ??? ?????? ?????? ?????? Cookie ??????
        String isLogin = "true";
        Date newExpTime = new Date(System.currentTimeMillis() + accExpTime);
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String expTime = sdf.format(newExpTime);
        CookieUtil.addPublicCookie(response, "isLogin", isLogin, cookieMaxAge);
        CookieUtil.addPublicCookie(response, "expTime", expTime, cookieMaxAge);
//        System.out.println("redis " + redisService.getValues(email));

        //mysql??? refresh token ??????
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .value(refreshToken)
                        .build()
        );

        return tokenProvider.createTokenDTO(accessToken,refreshToken, expTime);

    }

    @Transactional
    public TokenDTO reissue(TokenReqDTO tokenReqDTO,
                            HttpServletResponse response) {
        /*
         *  accessToken ??? JWT Filter ?????? ???????????? ???
         * */
//        String originAccessToken = HeaderUtil.getAccessToken(request);
//        String originRefreshToken = CookieUtil.getCookie(request, "refresh_token")
//                .map(Cookie::getValue)
//                .orElse((null));

        String originRefreshToken = tokenReqDTO.getRefreshToken();
//        System.out.println(originRefreshToken);
//        String originRefreshToken = tokenRequestDto.getRefreshToken(request);
//        String originAccessToken = tokenRequestDto.getAccessToken();
//        String originRefreshToken = tokenRequestDto.getRefreshToken();

        // refreshToken ??????
        int refreshTokenFlag = tokenProvider.validateToken(originRefreshToken);

        log.debug("refreshTokenFlag = {}", refreshTokenFlag);

        //refreshToken ???????????? ????????? ?????? ????????? ????????????.
        if (refreshTokenFlag == -1) {
            throw new BizException(JwtExceptionType.BAD_TOKEN); // ????????? ???????????? ??????
        } else if (refreshTokenFlag == 2) {
            throw new BizException(JwtExceptionType.REFRESH_TOKEN_EXPIRED); // ???????????? ?????? ??????
        }

        // 2. Access Token ?????? Member Email ????????????
        Authentication authentication = tokenProvider.getAuthentication(originRefreshToken);
//        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);
        log.debug("Authentication = {}", authentication);


        // Redis?????? mail???????????? refresh token??? ?????????.
        String rtkInRedis = redisService.getValues(authentication.getName());

        // Cache miss??? ???????????? ??????
        if (!rtkInRedis.equals(originRefreshToken)) {
            // DB?????? Member Email ??? ???????????? Refresh Token ??? ?????????
            RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new BizException(MemberExceptionType.LOGOUT_MEMBER)); // ?????? ????????? ?????????

            // Refresh Token ??????????????? ??????
            if (!refreshToken.getValue().equals(originRefreshToken)) {
                throw new BizException(JwtExceptionType.BAD_TOKEN); // ????????? ???????????? ????????????.
            }
        }


//        }

            Date newExpTime = new Date(System.currentTimeMillis() + accExpTime);
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String expTime = sdf.format(newExpTime);

            // 5. ????????? ?????? ??????
            String email = tokenProvider.getMemberEmailByToken(originRefreshToken);
//            String email = tokenProvider.getMemberEmailByToken(originAccessToken);
            Members members = customUserDetailsService.getMember(email);

            String newAccessToken = tokenProvider.createAccessToken(email, members.getAuthorities());
            String newRefreshToken = tokenProvider.createRefreshToken(email, members.getAuthorities());
            TokenDTO tokenDto = tokenProvider.createTokenDTO(newAccessToken, newRefreshToken, expTime);

            log.debug("refresh Origin = {}", originRefreshToken);
            log.debug("refresh New = {} ", newRefreshToken);

            // 6. Redis ?????? ????????????
            redisService.setValues(email, newRefreshToken, Duration.ofMillis(rtkLive));

            int cookieMaxAge = (int) rtkLive / 60;
//            CookieUtil.deleteCookie(request, response, "access_token");
//            CookieUtil.deleteCookie(request, response, "refresh_token");
//            CookieUtil.addCookie(response, "access_token", newAccessToken, cookieMaxAge);
            CookieUtil.addCookie(response, "refresh_token", newRefreshToken, cookieMaxAge);

            // ????????? ?????? ??? ?????? ?????? ?????? Cookie ??????
            String isLogin = "true";
            CookieUtil.addPublicCookie(response, "isLogin", isLogin, cookieMaxAge);
            CookieUtil.addPublicCookie(response, "expTime", expTime, cookieMaxAge);
//
//        // 6. ????????? ?????? ???????????? (dirtyChecking?????? ????????????)
//        refreshToken.updateValue(newRefreshToken);

            // ?????? ??????
//        return ApiResponse.success("token", newAccessToken);
            return tokenDto;
        }

    @Transactional
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){

        String originAccessToken = HeaderUtil.getAccessToken(request);

//        CookieUtil.deleteCookie(request, response, "access_token");
//        CookieUtil.deleteCookie(request, response, "refresh_token");
        String initValue = "";
//        CookieUtil.addCookie(response, "access_token", initValue,0);
        CookieUtil.addCookie(response, "refresh_token", initValue, 0);

        // ????????? ?????? ??? ?????? ?????? ?????? Cookie ??????
        String isLogin = "false";
        String expTime = "expTime";
        CookieUtil.addPublicCookie(response, "isLogin", isLogin, 0);
        CookieUtil.addPublicCookie(response, "expTime", expTime, 0);

        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);
        String email = authentication.getName();

        try{
            if(redisService.getValues(email).isEmpty()){
                if(refreshTokenRepository.findByEmail(email).isPresent()){
                    redisService.deleteValues(email);
                    refreshTokenRepository.deleteByEmail(email);
                }
            }
        } catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        redisService.deleteValues(email);
        refreshTokenRepository.deleteByEmail(email);

//        System.out.println(redisService.getValues(email));

        return ResponseEntity.ok("???????????? ???????????????.");
    }

    @Transactional
    public Optional<Members> isMember(MemberEmailDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            return memberRepository.findByEmail(memberRequestDto.getEmail());
        }else
            return null;
    }

    @Transactional
    public void updatePw(MemberUpdateDTO dto) {
        Members members = memberRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new BizException(MemberExceptionType.NOT_FOUND_USER));

        members.updateMember(dto, passwordEncoder);
    }

}
