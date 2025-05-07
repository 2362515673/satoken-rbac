package com.liu.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.entity.Menu;
import com.liu.rbac.model.vo.MenuVO;
import com.liu.rbac.model.vo.TreeMenuVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.mapper.MenuMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liun
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<TreeMenuVO> getTreeMenu() {
        List<MenuVO> list = lambdaQuery()
                .select(Menu::getId,
                        Menu::getParentId,
                        Menu::getName,
                        Menu::getCode,
                        Menu::getIcon,
                        Menu::getTitle,
                        Menu::getPath,
                        Menu::getUrl,
                        Menu::getType,
                        Menu::getOrder,
                        Menu::getCreateTime,
                        Menu::getUpdateTime)
                .list()
                .stream()
                .map(item -> BeanUtil.toBean(item, MenuVO.class))
                .collect(Collectors.toList());
        List<MenuVO> topMenu = list.stream().filter(item -> item.getParentId() == null).collect(Collectors.toList());

        return null;
    }

    @Override
    public Boolean saveMenu(SaveMenuDTO dto) {
        Menu menu = BeanUtil.toBean(dto, Menu.class);
        return save(menu);
    }
}




