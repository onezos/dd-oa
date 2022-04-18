package com.ddnotes.oa.service;

import com.ddnotes.oa.entity.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService = new UserService();
    @Test
    public void checkLogin1() {
        userService.checkLogin("uu", "uu");
    }
    @Test
    public void checkLogin2() {
        userService.checkLogin("m8", "1234");
    }
    @Test
    public void checkLogin3() {
        userService.checkLogin("m8", "test");
    }
    @Test
    public void selectNodeById() {
        List<Node> nodeList = userService.selectNodeByUserId(2l);
        System.out.println(nodeList);
    }
}