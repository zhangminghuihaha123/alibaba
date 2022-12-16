package com.zmh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmh.user.entry.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    public List<User> SelectQueryUser(HashMap map);

    @Select("Select count(*) from user where username like concat('%',#{username},'%')")
    Integer getTotal(@Param("username") String username);
}
