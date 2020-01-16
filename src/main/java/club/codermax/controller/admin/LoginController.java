package club.codermax.controller.admin;

import club.codermax.dao.UserRepository;
import club.codermax.entity.User;
import club.codermax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;


    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    /**
     *RedirectAttributes:目的是让session中的值能够跨越重定向，提供了model的所有功能
     *
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){

        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);

            // 在session中保存user
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            // 向前端传递信息
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
