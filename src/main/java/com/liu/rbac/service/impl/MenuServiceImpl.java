package com.liu.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.rbac.constant.ErrorCode;
import com.liu.rbac.exception.BaseException;
import com.liu.rbac.model.dto.SaveMenuDTO;
import com.liu.rbac.model.entity.Menu;
import com.liu.rbac.model.vo.MenuOptionsVO;
import com.liu.rbac.model.vo.MenuVO;
import com.liu.rbac.model.vo.TreeMenuVO;
import com.liu.rbac.service.MenuService;
import com.liu.rbac.mapper.MenuMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        List<TreeMenuVO> topMenu = list.stream()
                .filter(item -> item.getParentId() == null)
                .map(item -> {
                    TreeMenuVO treeMenuVO = new TreeMenuVO();
                    treeMenuVO.setMenu(item);
                    return treeMenuVO;
                }).collect(Collectors.toList());
        List<MenuVO> childrenList = list.stream().filter(item -> item.getParentId() != null).collect(Collectors.toList());
        topMenu.forEach(item -> getTreeRecursion(item, childrenList));
        return topMenu;
    }

    @Override
    public Boolean saveMenu(SaveMenuDTO dto) {
        Menu menu = BeanUtil.toBean(dto, Menu.class);
        if (ObjectUtils.isNotEmpty(dto.getParentId()) && !lambdaQuery().eq(Menu::getId, menu.getParentId()).exists()) {
            throw new BaseException(ErrorCode.PARAMS_ERROR, "父级菜单不存在");
        }
        return save(menu);
    }

    @Override
    public List<MenuOptionsVO> getTreeMenuOptions() {
        List<TreeMenuVO> treeMenu = getTreeMenu();
        return getTreeOptionsRecursion(treeMenu);
    }

    /**
     * 递归获取菜单树
     *
     * @param fatherMenu 父级顶级菜单
     * @param list       菜单列表
     */
    private void getTreeRecursion(TreeMenuVO fatherMenu, List<MenuVO> list) {
        List<TreeMenuVO> treeList = new ArrayList<>();
        list.forEach(item -> {
            if (item.getParentId().equals(fatherMenu.getMenu().getId())) {
                TreeMenuVO treeMenuVO = new TreeMenuVO();
                treeMenuVO.setMenu(item);
                treeList.add(treeMenuVO);
            }
        });
        fatherMenu.setChildren(treeList);
        if (CollectionUtils.isEmpty(treeList)) {
            return;
        }

        treeList.forEach(item -> getTreeRecursion(item, list));
    }

    private List<MenuOptionsVO> getTreeOptionsRecursion(List<TreeMenuVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        List<MenuOptionsVO> optionsList = new ArrayList<>();

        list.forEach(item -> {
            MenuOptionsVO menuOptionsVO = new MenuOptionsVO();
            menuOptionsVO.setLabel(item.getMenu().getTitle());
            menuOptionsVO.setKey(item.getMenu().getId());
            menuOptionsVO.setChildren(getTreeOptionsRecursion(item.getChildren()));
            optionsList.add(menuOptionsVO);
        });
        return optionsList;
    }
}




