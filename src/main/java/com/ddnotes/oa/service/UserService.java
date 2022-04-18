package com.ddnotes.oa.service;

import com.ddnotes.oa.dao.RbacDao;
import com.ddnotes.oa.dao.UserDao;
import com.ddnotes.oa.entity.Node;
import com.ddnotes.oa.entity.User;
import com.ddnotes.oa.service.exception.BussinessException;
import com.ddnotes.oa.utils.MD5Utils;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private RbacDao rbacDao = new RbacDao();
    /**
     * 根据用户名进行登录效验
     * @param username 前台输入的用户名
     * @param password 前台输入的密码
     * @return 效验通过后，包含对应用户数据的User实体类
     * @throws BussinessException L001-用户名不存在，L002-密码错误
     */
    public User checkLogin(String username, String password){
        User user = userDao.selectByUsername(username);
        //抛出用户不存在异常
        if(user == null){
            throw new BussinessException("L001","User name does not exist");
        }
        //对前台输入的密码加盐混淆后生成MD5，与保存在数据库中的MD5密码进行对比
        String md5 = MD5Utils.md5Digest(password, user.getSalt());
        if(!md5.equals(user.getPassword())){
            throw new BussinessException("L002","password error");
        }
        return user;
    }

    public List<Node> selectNodeByUserId(Long userId){
        List<Node> nodeList = rbacDao.selectNodeByUserId(userId);
        return nodeList;
    }
}
