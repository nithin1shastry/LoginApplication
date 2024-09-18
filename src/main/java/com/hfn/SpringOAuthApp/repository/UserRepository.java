package com.hfn.SpringOAuthApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.hfn.SpringOAuthApp.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByEmail(String emailId);
}