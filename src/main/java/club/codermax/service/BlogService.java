package club.codermax.service;

import club.codermax.entity.Blog;
import club.codermax.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {


    Blog saveBlog(Blog blog);

    Blog getBlog(Long id);

    Blog getBlogByName(String name);

    // 分页动态查询blog
    Page<Blog> blogList(Pageable pageable, BlogQuery blog);

    Page<Blog> blogList(Pageable pageable);


    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);


    Long CountBlog();

}
