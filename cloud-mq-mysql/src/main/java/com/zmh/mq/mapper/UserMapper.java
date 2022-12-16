package com.zmh.mq.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("${sql}")
    void SQLinsert(@Param("sql") String sql);

    @Update("${sql}")
    void SQLupdate(@Param("sql") String sql);

    @Delete("${sql}")
    void SQLdelete(@Param("sql") String sql);

}
