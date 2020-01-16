package club.codermax.service;

import club.codermax.entity.Type;
import club.codermax.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    // 检查用户名和密码
    User checkUser(String username,String password);

}
