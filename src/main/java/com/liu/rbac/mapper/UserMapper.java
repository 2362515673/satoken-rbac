package com.liu.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.rbac.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
