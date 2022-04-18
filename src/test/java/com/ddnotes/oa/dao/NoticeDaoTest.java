package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.Notice;
import com.ddnotes.oa.utils.MybatisUtils;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;

public class NoticeDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
           NoticeDao dao = sqlSession.getMapper(NoticeDao.class);
           Notice notice = new Notice();
           notice.setReceiverId(2l);
           notice.setContent("test message");
           notice.setCreateTime(new Date());
           dao.insert(notice);
           return null;
        });
    }
}