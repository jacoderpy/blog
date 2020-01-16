package club.codermax.service;

import club.codermax.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    // 保存
    Type saveType(Type type);

    Type getType(Long id);

    // 通过名称查询type，目的是为了后面业务判断是否重复
    Type getTypeByName(String name);

    // 分页查询
    Page<Type> listType(Pageable pageable);


    // 所有的数据
    List<Type> listType();

    // 修改,根据id返回一个新的Type
    Type updateType(Long id,Type type);

    //删除
    void deleteType(Long id);

}
