package com.hfn.SpringOAuthApp.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hfn.SpringOAuthApp.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByRole(String name);
}