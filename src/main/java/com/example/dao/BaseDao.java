package com.example.dao;

import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * @Auther: king
 * @Date: 2018/12/3 11:19
 * @Description:
 */
public interface BaseDao {

    public <T>List<T> queryAll(Object obj);

    public <T> Object queryEntityById(String id);

    public void addEntity(Object obj);

    public void deleteEntity(Integer id);

    public void editEntity(Object obj);

    public <T> Object queryEntity(Object obj);

}
