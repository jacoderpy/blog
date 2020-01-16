package club.codermax.dao;

import club.codermax.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



// 继承之后，有关数据库增删改查之类的细节不需要操控

/**
 * JpaRepository<操作的实体类类型，实体类中主键属性的类型>
 *     封装的CRUD操作
 *  JpaSpecificationExecutor<操作的实体类类型>
 *      封装了复杂查询（分页）
 *
 */
public interface UserRepository  extends JpaRepository<User,Long> {
    // 定义方法，遵循它的命名规则
    User findByUsernameAndPassword(String username,String password);
}
