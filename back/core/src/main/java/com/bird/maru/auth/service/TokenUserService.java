package com.bird.maru.auth.service;

import com.bird.maru.auth.service.dto.CustomUserDetails;
import com.bird.maru.common.util.RestUtil;
import com.bird.maru.domain.model.entity.Member;
import com.bird.maru.domain.model.type.Provider;
import com.bird.maru.member.repository.MemberRepository;
import com.bird.maru.member.repository.query.MemberQueryRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implicit Grant 방식을 지원합니다. <br>
 * 이 빈은 ImplicitOAuth2LoginAuthenticationFilter에서 호출합니다. <br>
 * Provider와 Access-Token을 이용하여 Resource Server로부터 사용자 정보를 받고, 서비스에 맞는 UserDetails 객체를 생성합니다.
 */
@Service
public class TokenUserService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final String googleUserInfoUri;
    private final String naverUserInfoUri;
    private final String kakaoUserInfoUri;

    public TokenUserService(
            MemberRepository memberRepository,
            MemberQueryRepository memberQueryRepository,
            @Value("${user-info-uri.google}") String googleUserInfoUri,
            @Value("${user-info-uri.naver}") String naverUserInfoUri,
            @Value("${user-info-uri.kakao}") String kakaoUserInfoUri
    ) {
        this.memberRepository = memberRepository;
        this.memberQueryRepository = memberQueryRepository;
        this.naverUserInfoUri = naverUserInfoUri;
        this.kakaoUserInfoUri = kakaoUserInfoUri;
        this.googleUserInfoUri = googleUserInfoUri;
    }

    @Transactional
    public OAuth2User loadUser(Provider provider, String accessToken) {
        switch (provider) {
            case GOOGLE:
                return loadUserByGoogle(accessToken);
            case NAVER:
                return loadUserByNaver(accessToken);
            case KAKAO:
                return loadUserByKakao(accessToken);
            default:
                throw new RuntimeException("이 코드는 실행될 수 없습니다.");
        }
    }

    private OAuth2User loadUserByGoogle(String accessToken) {
        Map<String, Object> attributes = RestUtil.getUserInfo(googleUserInfoUri, accessToken);
        return createUserDetails(attributes, Provider.GOOGLE);
    }

    private OAuth2User loadUserByNaver(String accessToken) {
        Map<String, Object> attributes = RestUtil.getUserInfo(naverUserInfoUri, accessToken);
        return createUserDetails(attributes, Provider.NAVER);
    }

    private OAuth2User loadUserByKakao(String accessToken) {
        Map<String, Object> attributes = RestUtil.getUserInfo(kakaoUserInfoUri, accessToken);
        return createUserDetails(attributes, Provider.KAKAO);
    }

    private OAuth2User createUserDetails(Map<String, Object> attributes, Provider provider) {
        CustomUserDetails userDetails = CustomUserDetails.of(attributes, provider);
        memberQueryRepository.findByEmailAndProvider(userDetails.getEmail(), userDetails.getProvider())
                             .ifPresentOrElse(
                                     member -> userDetails.setId(member.getId()),
                                     () -> join(userDetails)
                             );
        return userDetails;
    }

    private void join(CustomUserDetails userDetails) {
        Member member = Member.builder()
                              .nickname(userDetails.getNickname())
                              .email(userDetails.getEmail())
                              .provider(userDetails.getProvider())
                              .build();

        memberRepository.save(member);
        userDetails.setId(member.getId());
    }

}
