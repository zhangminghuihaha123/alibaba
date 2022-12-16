package com.zmh.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmh.role.entry.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("Select count(*) from role;")
    Integer selectTotal();
}
