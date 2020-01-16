package club.codermax.dao;

import club.codermax.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeRepository extends JpaRepository<Type,Long>, JpaSpecificationExecutor<Type> {


    // 定义通过名称查询Type的方法
    Type findByName(String name);

}
