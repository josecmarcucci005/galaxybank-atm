package com.galaxybank.repository;

import com.galaxybank.model.AdminUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends CrudRepository<AdminUser, Long> {

    List<AdminUser> findByEmailAndPassword(@Param("email") String email,
                                           @Param("password") String password);
}
