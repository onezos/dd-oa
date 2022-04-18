package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.Node;
import com.ddnotes.oa.utils.MybatisUtils;

import java.util.List;

public class RbacDao {
    public List<Node> selectNodeByUserId(Long userId){
        return (List)MybatisUtils.executeQuery(sqlSession->sqlSession.selectList("rbacmapper.selectNodeByUserId",userId));
    }
}
