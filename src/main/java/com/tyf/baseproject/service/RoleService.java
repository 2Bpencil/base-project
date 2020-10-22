package com.tyf.baseproject.service;

import com.alibaba.fastjson.JSONArray;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.base.datapage.PageGetter;
import com.tyf.baseproject.dao.MenuRepository;
import com.tyf.baseproject.dao.RoleRepository;
import com.tyf.baseproject.entity.Menu;
import com.tyf.baseproject.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Service
public class RoleService extends PageGetter<Role> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;

    /**
     * 获取所有角色并且获取其所有的菜单
     * @return
     */
    public List<Role> findAll() {
        List<Role> allRoles = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role -> {
            List<Menu> menus = menuRepository.findByRoleId(role.getId());
            role.setMenus(menus);
            allRoles.add(role);
        });
        return allRoles;
    }

    /**
     * 角色树
     * @return
     */
    public String findRoleTree(){
        List<Role> list = roleRepository.findAll();
        List<Map<String,Object>> treeList = new ArrayList<>();
        list.forEach(role -> {
            Map<String,Object> map = new HashMap<>();
            map.put("name",role.getName());
            map.put("id",role.getId());
            map.put("pId","root");
            map.put("icon","/img/icon/role.png");
            treeList.add(map);
        });
        return JSONArray.toJSONString(treeList);
    }

    public List<Role> findByMenuId(Integer id) {
        return roleRepository.findByMenuId(id);
    }


    /**
     * 分页查询
     * @param parameterMap
     * @return
     */
    public DataPage<Role> getDataPage(Map<String,String[]> parameterMap){
        return super.getPages(parameterMap);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    public Role saveEntity(Role role){
        return roleRepository.save(role);
    }

    /**
     * 删除实体
     * @param id
     */
    public void deleteRole(Integer id){
        roleRepository.deleteById(id);
        roleRepository.deleteRoleAndUser(id);
    }

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public Role getRoleById(Integer id){
        return roleRepository.getOne(id);
    }

    /**
     * 根据id获取菜单
     * @param roleId
     * @return
     */
    public String getMenusByRoleId(Integer roleId){
        List<Menu> list = menuRepository.findByRoleId(roleId);
        return JSONArray.toJSONString(list);
    }


    /**
     * 保存角色菜单的关系
     * @param roleId
     * @param menuIds
     */
    public void saveRsRoleMenu(Integer roleId,String menuIds){
        roleRepository.deleteRoleAndMenu(roleId);
        if(StringUtils.isNotBlank(menuIds)){
            Stream.of(menuIds.split(",")).forEach(id->{
                roleRepository.saveRoleAndMenu(roleId,Integer.parseInt(id));
            });
        }
    }

    /**
     * 判断重复
     * @param authority
     * @param id
     * @return
     */
    public boolean verifyTheRepeat(String authority,String id){
        Integer num = null;
        if(StringUtils.isNotBlank(id)){
            num = roleRepository.getRoleNumByIdAndAuthority(Integer.parseInt(id), authority);
        }else{
            num = roleRepository.getRoleNumByAuthority( authority);
        }
        return num > 0?false:true;
    }

    /**
     * 判断角色是否被使用
     * @param id
     * @return
     */
    public Boolean checkRoleUsed(Integer id){
        Integer num = roleRepository.getRsRoleUserNumByRoleId(id);
        if(num > 0){
            return false;
        }else{
            return true;
        }
    }

}
