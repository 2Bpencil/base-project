package com.tyf.baseproject.dao;


import com.tyf.baseproject.base.repository.ExpandJpaRepository;
import com.tyf.baseproject.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends ExpandJpaRepository<Menu,Integer> {

    @Query(value = "select  m.* from menu m left join r_role_menu rm on m.id = rm.menu_id left join role r on r.id = rm.role_id where r.id = ?1 ", nativeQuery = true)
    List<Menu> findByRoleId(Integer roleId);
    @Query(value = "select DISTINCT m.* from menu m left join r_role_menu rm on m.id = rm.menu_id left join role r on r.id = rm.role_id where r.id in (select r2.id " +
            " from role r2 left join r_user_role ur on ur.role_id = r2.id where ur.user_id = ?1) ORDER BY m.sort ASC ", nativeQuery = true)
    List<Menu> findByUserId(Integer userId);

    @Query(value = "select * from menu ORDER BY sort ASC ", nativeQuery = true)
    List<Menu> findAllBySort();

    @Query(value = "select count(*) from menu where code = ?2 and id != ?1", nativeQuery = true)
    Integer getMenuNumByIdAndCode(Integer id, String code);

    @Query(value = "select count(*) from menu where code = ?1", nativeQuery = true)
    Integer getMenuNumByCode(String code);

    /**
     * 根据菜单id获取角色菜单关系
     * @param menuId
     * @return
     */
    @Query(value = "select count(*) from r_role_menu where menu_id = ?1", nativeQuery = true)
    Integer getRsRoleMenuNumByMenuId(Integer menuId);

    /**
     *
     * @param pid
     * @return
     */
    @Query(value = "select DISTINCT m.* from menu m left join r_role_menu rm on m.id = rm.menu_id left join role r on r.id = rm.role_id where r.id in (select r2.id " +
            " from role r2 left join r_user_role ur on ur.role_id = r2.id where ur.user_id = ?1 and m.pid = ?2) ORDER BY m.sort ASC ", nativeQuery = true)
    List<Menu> getMenuByUserIdAndPid(Integer userId, Integer pid);
}
