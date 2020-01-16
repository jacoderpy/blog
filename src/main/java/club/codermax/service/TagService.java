package club.codermax.service;

import club.codermax.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    // 分页查询
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    // 修改,根据id返回一个新的Type
    Tag updateTag(Long id, Tag tag);

    //删除
    void deleteTag(Long id);

}
