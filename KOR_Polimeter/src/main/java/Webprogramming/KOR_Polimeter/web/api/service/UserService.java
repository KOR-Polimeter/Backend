package Webprogramming.KOR_Polimeter.web.api.service;

import Webprogramming.KOR_Polimeter.web.api.model.User;
import Webprogramming.KOR_Polimeter.web.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        System.out.println(user.getName() + " 사용자의 정보가 DB에 저장되었습니다.");
        return userRepository.save(user);
    }
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public boolean userExists(Long UserId) {
        return userRepository.existsByUserId(UserId);
    }
}