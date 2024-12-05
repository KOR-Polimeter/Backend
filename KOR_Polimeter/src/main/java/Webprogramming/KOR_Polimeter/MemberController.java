package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.service.KakaoService;
import Webprogramming.KOR_Polimeter.service.MemberService;
import Webprogramming.KOR_Polimeter.dto.KakaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static Webprogramming.KOR_Polimeter.dto.KakaoDTO.convertToMember;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/saveMember")
    public String saveMember(@RequestBody KakaoDTO kakaoDTO) {
        memberService.saveMember(convertToMember(kakaoDTO));
        return "Member saved successfully!";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Bearer 토큰에서 실제 액세스 토큰 추출
            String accessToken = authorizationHeader.replace("Bearer ", "");

            // 서비스에서 로그아웃 처리
            KakaoService.logoutUser(accessToken);

            return ResponseEntity.ok("로그아웃 성공");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("로그아웃 실패: " + e.getMessage());
        }
    }
}
