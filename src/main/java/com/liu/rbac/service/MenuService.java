package com.liu.rbac.service;

import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.rbac.model.vo.TreeMenuVO;

import java.util.List;

/**
 * @author liun
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<TreeMenuVO> getTreeMenu();

    /**
     * 添加菜单
     *
     * @param dto 菜单信息
     * @return 是否添加成功
     */
    Boolean saveMenu(SaveMenuDTO dto);
}
