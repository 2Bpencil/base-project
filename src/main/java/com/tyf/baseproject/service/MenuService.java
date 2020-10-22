package com.tyf.baseproject.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tyf.baseproject.dao.MenuRepository;
import com.tyf.baseproject.entity.Menu;
import com.tyf.baseproject.entity.Tree;
import com.tyf.baseproject.entity.TreeTable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Auther: tyf
 * @Date: 2019/7/9 19:55
 * @Description:
 */
@Service
public class MenuService  {

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 根据用户id获取该用户所有的菜单权限json数组
     * @param userId
     * @return
     */
    public String getMenusByUserId(Integer userId){
        List<Map<String,Object>> menuList = new ArrayList<>();
        List<Menu> list = menuRepository.findByUserId(userId);
        list.stream().filter(menu -> menu.getPid() == 0).forEach(menu -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name",menu.getName());
            map.put("url",menu.getUrl());
            map.put("icon",menu.getIcon());
            map.put("type",menu.getType());
            //  type 0 是父级菜单  1是叶子菜单
            if(menu.getType()==0){
                //父级  获取其下级所有叶子节点
                List<Menu> childrenMenus = menuRepository.getMenuByUserIdAndPid(userId,menu.getId());
                map.put("children",childrenMenus);
            }
            menuList.add(map);
        });
        return JSONArray.toJSONString(menuList);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public String getAllMenus(){
        List<Menu> list = menuRepository.findAllBySort();
        List<TreeTable> treeTables = new ArrayList<TreeTable>();
        list.forEach(menu -> {
            TreeTable treeTable = new TreeTable();
            treeTable.setId(menu.getId()+"");
            treeTable.setpId(menu.getPid()==0?"":menu.getPid().toString());
            treeTable.setName(menu.getName());
            String[] td = {menu.getCode(),menu.getUrl(),menu.getType()==0?"父级菜单":"叶子菜单",menu.getIcon()==null?"":menu.getIcon(),menu.getSort()==null?"":menu.getSort().toString()};
            treeTable.setTd(td);
            if(menu.getType()==0){
                treeTable.setIcon("/img/icon/menus.png");
            }else{
                treeTable.setIcon("/img/icon/menu.png");
            }
            treeTables.add(treeTable);
        });
        return JSONArray.toJSONString(treeTables);
    }

    public String getAllMenusForTree(){
        List<Menu> list = menuRepository.findAllBySort();
        List<Tree> treeList = new ArrayList<>();
        list.forEach(menu -> {
            Tree tree = new Tree();
            tree.setId(menu.getId()+"");
            tree.setpId(menu.getPid()==0?"":menu.getPid().toString());
            tree.setName(menu.getName());
            if(menu.getType()==0){
                tree.setIcon("/img/icon/menus.png");
            }else{
                tree.setIcon("/img/icon/menu.png");
            }
            treeList.add(tree);
        });
        return JSONArray.toJSONString(treeList);
    }


    /**
     * 保存菜单
     * @param menu
     * @return
     */
    public Menu saveEntity(Menu menu){
        return menuRepository.save(menu);
    }

    /**
     *
     * @param code
     * @param id
     * @return判断重复
     */
    public boolean verifyTheRepeat(String code,String id){
        Integer num = null;
        if(StringUtils.isNotBlank(id)){
            num = menuRepository.getMenuNumByIdAndCode(Integer.parseInt(id), code);
        }else{
            num = menuRepository.getMenuNumByCode( code);
        }
        return num > 0?false:true;
    }

    /**
     * 获取菜单实体
     * @param id
     * @return
     */
    public Menu getMenuById(Integer id){
        return menuRepository.getOne(id);
    }

    /**
     * 根据id获取编辑信息
     * @param id
     * @return
     */
    public String getEditInfo(Integer id){
        Map<String,Object> map = new HashMap<>();
        Menu menu = menuRepository.getOne(id);
        map.put("id",menu.getId());
        map.put("name",menu.getName());
        map.put("sort",menu.getSort());
        map.put("pid",menu.getPid());
        map.put("code",menu.getCode());
        map.put("type",menu.getType());
        map.put("icon",menu.getIcon());
        map.put("url",menu.getUrl());
        if(menu.getPid()!=0){
            map.put("parent",menuRepository.getOne(menu.getPid()).getName());
        }else{
            map.put("parent","");
        }
        return JSONObject.toJSONString(map);
    }


    /**
     * 判断菜单是否被使用
     * @param ids
     * @return
     */
    public Boolean checkMenuUsed(String ids){
        for (String id : ids.split(",")) {
            Integer num = menuRepository.getRsRoleMenuNumByMenuId(Integer.parseInt(id));
            if(num > 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 删除菜单
     * @param ids
     */
    public void deleteMenu(String ids){
        Stream.of(ids.split(",")).forEach(id->{
            menuRepository.deleteById(Integer.parseInt(id));
        });
    }


}
