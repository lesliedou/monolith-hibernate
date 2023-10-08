package com.begcode.demo.hibernate.web.rest;

import static com.begcode.demo.hibernate.security.SecurityUtils.AUTHORITIES_KEY;
import static com.begcode.demo.hibernate.security.SecurityUtils.JWT_ALGORITHM;
import static com.begcode.demo.hibernate.security.SecurityUtils.USER_ID;

import com.begcode.demo.hibernate.domain.User;
import com.begcode.demo.hibernate.security.UserNotActivatedException;
import com.begcode.demo.hibernate.service.UserService;
import com.begcode.demo.hibernate.service.dto.AdminUserDTO;
import com.begcode.demo.hibernate.service.dto.UserDTO;
import com.begcode.demo.hibernate.system.service.SmsService;
import com.begcode.demo.hibernate.web.rest.vm.LoginMobileVM;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import javax.cache.CacheManager;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserSmsController extends AuthenticateController {

    public static final String PARAM_KEY = "code";

    private final SmsService smsService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public UserSmsController(
        JwtEncoder jwtEncoder,
        AuthenticationManagerBuilder authenticationManagerBuilder,
        SmsService smsService,
        UserDetailsService userDetailsService,
        UserService userService,
        CacheManager cacheManager
    ) {
        super(jwtEncoder, authenticationManagerBuilder, cacheManager);
        this.smsService = smsService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/authenticate/mobile")
    public ResponseEntity<JWTToken> authorizeByMobile(@Valid @RequestBody LoginMobileVM loginMobileVM) {
        // 验证动态口令与图片验证码
        boolean result = smsService.validateMessage(loginMobileVM.getMobile(), loginMobileVM.getCode());
        if (result) {
            // 通过mobile查找用户信息。
            Optional<UserDTO> userDTO = userService.findOneByMobile(loginMobileVM.getMobile());
            if (userDTO.isPresent()) {
                AdminUserDTO userOptional = userService.getUserWithAuthorities(userDTO.orElseThrow().getId()).orElse(null);
                if (userOptional != null) {
                    UserDetails user = userDetailsService.loadUserByUsername(userOptional.getLogin());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    boolean rememberMe = loginMobileVM.isRememberMe() != null && loginMobileVM.isRememberMe();
                    String jwt = this.createTokenWithUserId(authenticationToken, rememberMe, userOptional.getId());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setBearerAuth(jwt);
                    return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
                } else {
                    throw new UsernameNotFoundException("未找到指定手机号码用户");
                }
            } else {
                throw new UsernameNotFoundException("未找到指定手机号码用户");
            }
        } else {
            // 未通过
            throw new UserNotActivatedException("验证未通过");
        }
    }

    /**
     *
     * 发送验证码到手机登录时使用
     *
     */
    @GetMapping("/mobile/smscode")
    public ResponseEntity<Void> getSmsCode(String mobile, @RequestParam(name = "imageCode", required = false) String imageCode) {
        Optional<UserDTO> userDTO1 = userService.findOneByMobile(mobile);
        if (!userDTO1.isPresent()) {
            // todo，不应该在这里新注册，应该在登录验证里处理 使用手机号码注册一个新用户
            AdminUserDTO userDTO = new AdminUserDTO();
            userDTO.setActivated(true);
            userDTO.setLogin(mobile);
            userDTO.setEmail(mobile + "@jhipster.pro");
            userDTO.setMobile(mobile);
            User user = userService.createUser(userDTO);
        }
        // 发送手机短信验证码
        Boolean success = smsService.sendValidate(mobile);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     *
     * 发送验证码到手机，用户设置手机号码时使用
     *
     */
    /* @GetMapping("/mobile/smscode/current-user")
    public ResponseEntity<Result<String>> getSmsCodeByCurrentUser(String mobile, @RequestParam(name = "imageCode", required = false) String imageCode) {
        // 首先检查是否已经被别人注册。

        // 未注册的情况下直接发送短信验证码

        // 提交时再注册到用户的person中。
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            Boolean existsMobile = userService.existsByMobileAndLoginNot(mobile, SecurityUtils.getCurrentUserLogin().get());
            if (!existsMobile) {
                // 不存在，发送验证码
                Result<String> result = daYuSmsService.sendVerifyCode(mobile, "monolith-hibernate");
                if (result.getSuccess()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.badRequest().body(Result.error(ErrorEnum.UNKNOWN));
                }
            } else {
                return ResponseEntity.badRequest().body(Result.error(ErrorEnum.MOBILE_EXISTED));
            }
        } else {
            return ResponseEntity.badRequest().body(Result.error(ErrorEnum.UNKNOWN));
        }
    } */

    /**
     *
     * 保存当前用户设置的手机号码
     *
     */
    @PostMapping("/mobile/current-user")
    public ResponseEntity<Void> saveMobileByCurrentUser(
        String mobile,
        String code,
        @RequestParam(name = "imageCode", required = false) String imageCode
    ) {
        // 验证
        boolean result = smsService.validateMessage(mobile, code);
        if (result) {
            userService.updateUserMobile(mobile);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public String createTokenWithUserId(Authentication authentication, boolean rememberMe, Long id) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID, String.valueOf(id))
            .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
