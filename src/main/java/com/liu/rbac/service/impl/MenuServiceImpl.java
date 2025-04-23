package com.liu.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.model.entity.Menu;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author liun
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




