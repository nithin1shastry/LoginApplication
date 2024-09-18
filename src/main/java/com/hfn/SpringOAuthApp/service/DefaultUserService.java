package com.hfn.SpringOAuthApp.service;



import org.springframework.security.core.userdetails.UserDetailsService;

import com.hfn.SpringOAuthApp.DTO.UserRegisteredDTO;
import com.hfn.SpringOAuthApp.model.User;


public interface DefaultUserService extends UserDetailsService{

    User save(UserRegisteredDTO userRegisteredDTO);

}
