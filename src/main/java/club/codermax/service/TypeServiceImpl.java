package club.codermax.service;

import club.codermax.NotFoundException;
import club.codermax.dao.TypeRepository;
import club.codermax.entity.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    //注入dao
    @Autowired
    private TypeRepository typeRepository;

    // 要放入到事务之中
    @Transactional
    @Override
    public Type saveType(Type type) {
        //时间
        type.setCreateTime(new Date());
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id)
    {
        return typeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        //BeanUtils.copyProperties将type中的值赋值给t
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
