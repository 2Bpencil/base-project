package com.tyf.baseproject.service;

import com.alibaba.fastjson.JSONArray;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.base.datapage.PageGetter;
import com.tyf.baseproject.dao.RoleRepository;
import com.tyf.baseproject.dao.UserRepository;
import com.tyf.baseproject.entity.Role;
import com.tyf.baseproject.entity.User;
import com.tyf.baseproject.ustil.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Service
public class UserService extends PageGetter<User> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(name);
        if (user != null) {
            List<Role> roles = roleRepository.findByUserId(user.getId());
            System.out.println(roles.size());
            user.setRoles(roles);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Role role : roles) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getAuthority());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
            }
            return user;
        } else {throw new UsernameNotFoundException("admin: " + name + " do not exist!");

        }
    }

    /**
     * 保存方法
     * @param user
     * @return
     */
    public User saveEntity(User user) {
        return userRepository.save(user);
    }

    /**
     * 分页查询
     * @param parameterMap
     * @return
     */
    public DataPage<User> getDataPage(Map<String,String[]> parameterMap){
        return super.getPages(parameterMap);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
        userRepository.deleteRoleAndUser(id);
    }

    /**
     * 获取用户
     * @param id
     * @return
     */
    public User getUserById(Integer id){
        return userRepository.getOne(id);
    }

    /**
     * 获取用户下的角色
     * @param id
     * @return
     */
    public String getRolesByUserId(Integer id){
        List<Role> list = roleRepository.findByUserId(id);
        return JSONArray.toJSONString(list);
    }

    /**
     * 保存用户角色
     * @param userId
     * @param roleIds
     */
    public void saveUserAndRole(Integer userId,String roleIds){
        userRepository.deleteRoleAndUser(userId);
        if(StringUtils.isNotBlank(roleIds)){
            Stream.of(roleIds.split(",")).forEach(id->{
                userRepository.saveRoleAndUser(userId,Integer.parseInt(id));
            });
        }
    }

    /**
     *保存班级配置
     * @param userId
     * @param classIds
     */
    public void saveClassSet(Integer userId,String classIds){
        userRepository.deleteClassAndUser(userId);
        if(StringUtils.isNotBlank(classIds)){
            Stream.of(classIds.split(",")).forEach(id->{
                userRepository.saveClassSet(userId,Integer.parseInt(id));
            });
        }
    }

    /**
     * 验证
     * @param username
     * @param id
     * @return
     */
    public boolean verifyTheRepeat(String username,String id){
        Integer num = null;
        if(StringUtils.isNotBlank(id)){
            num = userRepository.getUserNumByIdAndUsername(Integer.parseInt(id), username);
        }else{
            num = userRepository.getRoleNumByUsername( username);
        }
        return num > 0?false:true;
    }

    /**
     *
     * @return
     */
    public boolean checkPassword(String password){
        String username = SecurityUtil.getCurUserName();
        User user = userRepository.findUserByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPassword = user.getPassword();
        return passwordEncoder.matches(password,oldPassword);
    }

    /**
     * 保存密码
     * @param password
     */
    public void savePassword(String password){
        String username = SecurityUtil.getCurUserName();
        User user = userRepository.findUserByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }


    /**
     * 重置密码
     * @param id
     */
    public void reSetPassword(Integer id){
        User user = userRepository.getOne(id);
        user.setPassword("123456");
        userRepository.save(user);
    }


}
