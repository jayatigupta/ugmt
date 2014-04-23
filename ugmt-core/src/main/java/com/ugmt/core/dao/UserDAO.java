package com.ugmt.core.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ugmt.core.entity.User;

@Repository
public interface UserDAO extends JpaDAO<User, Serializable> {

    List<User> findByName(String name);
    User findByEmail(String email);
}
