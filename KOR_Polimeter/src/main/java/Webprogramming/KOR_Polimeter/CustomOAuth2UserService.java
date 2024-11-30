package Webprogramming.KOR_Polimeter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User;

        try {
            oAuth2User = delegate.loadUser(userRequest);
            // 사용자 정보 처리
            String email = oAuth2User.getAttribute("kakao_account.email");
            String nickname = oAuth2User.getAttribute("properties.nickname");
            logger.info("Email: {}", email);
            logger.info("Nickname: {}", nickname);
        } catch (Exception e) {
            logger.error("Error loading user info: {}", e.getMessage());
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user_info"), e);
        }

        return oAuth2User;
    }
}
