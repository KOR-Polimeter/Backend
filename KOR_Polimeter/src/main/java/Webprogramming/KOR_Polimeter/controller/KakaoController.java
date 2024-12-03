package Webprogramming.KOR_Polimeter.controller;

import Webprogramming.KOR_Polimeter.MsgEntity;
import Webprogramming.KOR_Polimeter.dto.KakaoDTO;
import Webprogramming.KOR_Polimeter.service.KakaoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RESTful 웹 서비스 컨트롤러
@RequiredArgsConstructor // final로 선언된 필드 값을 가진 생성자를 자동으로 생성
@RequestMapping("kakao") // 해당 컨트롤러의 모든 메서드에 대한 기본 경로를 /kakao로 지정
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback") // /kakao/callback 경로
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        return ResponseEntity.ok()
                .body(new MsgEntity("Success", kakaoInfo));
    }
}
