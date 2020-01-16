package club.codermax.controller.admin;


import club.codermax.entity.Blog;
import club.codermax.entity.User;
import club.codermax.service.BlogService;
import club.codermax.service.TagService;
import club.codermax.service.TypeService;
import club.codermax.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @GetMapping("/blog")
    public String blog(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.blogList(pageable, blog));
        return "admin/blog";
    }

    @GetMapping("/blog/input")
    public String input(Model model) {
        model.addAttribute("blog", new Blog());
        return "admin/blog-input";
    }

    // 处理搜索之后的结果，保证搜索框中的内容不变，采用aja请求,thymeleaf中的片段
    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.blogList(pageable, blog));
        // 即查询结果只返回到对应前端的代码片段
        return "admin/blog::blogList";
    }


    //新增
    @PostMapping("/blog")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        // 注意redirect冒号后面不能有空格
        return "redirect:/admin/blog";
    }

    //删除
    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blog";
    }
}
