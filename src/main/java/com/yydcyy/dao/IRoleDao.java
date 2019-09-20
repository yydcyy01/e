package com.yydcyy.dao;

import com.yydcyy.domain.Role;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-20
 */
public interface IRoleDao {
    List<Role> findAll();
}
