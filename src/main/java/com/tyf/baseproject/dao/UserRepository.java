package com.tyf.baseproject.dao;

import com.tyf.baseproject.base.repository.ExpandJpaRepository;
import com.tyf.baseproject.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends ExpandJpaRepository<User,Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO r_classes_user(user_id,classes_id) VALUES(?1,?2)", nativeQuery = true)
    void saveClassSet(Integer userId, Integer classesId);

    @Transactional
    @Modifying
    @Query(value = "delete from r_classes_user where user_id = ?1", nativeQuery = true)
    void deleteClassAndUser(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO r_user_role(user_id,role_id) VALUES(?1,?2)", nativeQuery = true)
    void saveRoleAndUser(Integer userId, Integer roleId);


    @Transactional
    @Modifying
    @Query(value = "delete from r_user_role where user_id = ?1", nativeQuery = true)
    void deleteRoleAndUser(Integer userId);



    @Query(value = "select count(*) from user where username = ?2 and id != ?1", nativeQuery = true)
    Integer getUserNumByIdAndUsername(Integer id, String username);

    @Query(value = "select count(*) from user where username = ?1", nativeQuery = true)
    Integer getRoleNumByUsername(String username);

    User findUserByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT rcu.user_id FROM  r_classes_user rcu WHERE rcu.classes_id in ( SELECT c.id FROM classes c  WHERE pid = ?1 AND c.name LIKE  CONCAT('%',?2,'%')  )) a", nativeQuery = true)
    Integer getUserNumByClassPid(Integer id, String subject);

}
