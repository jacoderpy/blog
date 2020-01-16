package club.codermax.controller.admin;

import club.codermax.entity.Type;
import club.codermax.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    // service注入
    @Autowired
    private TypeService typeService;

    // PageableDefault指定分页的数据sort = {"id"},direction = Sort.Direction.DESC按照id倒序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        // springboot默认封装好的分页的方法typeService.listType(pageable);
        //model.addAttribute获得前端页面的对象
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }


    // 标签新增功能
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }


    //提交标签新增
    //Valid代表要校验type对象,目的是检验新增是否为空,BindingResult接收校验之后的结果，是为了校验不能为空采取的措施
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        //判断新增是否重复，在这个判断重复问题中出现有模板引擎的bug
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            //自定义新增错误信息
            result.rejectValue("name", "nameError", "不能重添加重复的分类");
        }
        if (result.hasErrors()) {
            // 直接返回新增页面
            return "admin/type-input";
        }
        // 调用typeService方法进行保存type（type接收type-input.html中form表单中name的值）
        Type t = typeService.saveType(type);
        if (t == null) {
            // 没新增成功
            // 如果是重定向使用RedirectAttributes才能拿到之后的提示信息
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            // 新增成功
            attributes.addFlashAttribute("message", "新增成功");

        }
        return "redirect:/admin/types";
    }


    // 根据id查询要修改的type
    @GetMapping("types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }


    // 重新编辑type，并将原来的type换成新改之后的type，根据id
    //BindingResult必须要和校验的参数紧挨着type，否则没有效果
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        //判断新增是否重复，在这个判断重复问题中出现有模板引擎的bug
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            //自定义新增错误信息
            result.rejectValue("name", "nameError", "不能重添加重复的分类");
        }

        if (result.hasErrors()) {
            // 直接返回新增页面
            return "admin/type-input";
        }
        // 调用typeService方法进行保存type（type接收type-input.html中form表单中name的值）
        Type t = typeService.updateType(id, type);
        if (t == null) {
            // 没新增成功
            // 如果是重定向使用RedirectAttributes才能拿到之后的提示信息
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            // 新增成功
            attributes.addFlashAttribute("message", "更新成功");

        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }


}
