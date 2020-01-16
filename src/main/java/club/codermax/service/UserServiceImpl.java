package club.codermax.service;

import club.codermax.dao.UserRepository;
import club.codermax.entity.User;
import club.codermax.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    // 依赖注入，数据库操作对象
    @Autowired
    private UserRepository userRepository;
    // 检查用户名和密码,返回对应的用户
    // MD5Utils.code() MD5加密，避免明码在网络上传输
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
