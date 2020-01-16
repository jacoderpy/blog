package club.codermax.service;

import club.codermax.NotFoundException;
import club.codermax.dao.BlogRepository;
import club.codermax.entity.Blog;
import club.codermax.entity.Type;
import club.codermax.utils.MyBeanUtils;
import club.codermax.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Blog getBlogByName(String name) {
        return null;
    }


    @Transactional
    @Override
    public Page<Blog> blogList(Pageable pageable, BlogQuery blog) {
        // springboot JPA中封装好高级的动态的组合查询，需要repository中继承接口JpaSpecificationExecutor


        // findAll,此时需要传递两个参数，
        // 第一参数相当于查询条件，criteriaQuery：将查询条件放入其中，criteriaBuilder：设置某一个具体条件的表达式
        // pageable第二个参数
        return blogRepository.findAll(new Specification<Blog>() {
            // 匿名内部类
            /**
             * 自定义查询条件
             *  1.实现Specification接口（提供泛型：查询的对象类型）
             *  2.实现toPredicate方法（构造查询条件）
             *  3.需要借助方法参数中的两个参数（
             *       root：获取需要查询的对象属性
             *       CriteriaQuery：构造查询条件的，内部封装的许多的查询条件（模糊匹配、精准匹配）
             *     ）
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 组合条件要放到这个list中
                List<Predicate> predicates = new ArrayList<>();
                // 根据标题进行是否非空判断
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //以下是组合条件
                    // like,数据库的模糊查询,并且自己需要进行拼接操作
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+ blog.getTitle()+"%"));
                }
                // 根据分类进行查询
                if(blog.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //组合查询，where类似数据库中的where，需要传递的是一个数组，而开始构造的是list，所以要用toArray转换
                //predicates.size()指定大小长度
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> blogList(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }


    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


    @Transactional
    @Override
    public Long CountBlog() {
        return null;
    }
}
