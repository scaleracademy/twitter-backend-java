package xyz.subho.clone.twitter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.security.UsersAuthenticationEntity;

public interface UsersAuthenticationRepository extends JpaRepository<UsersAuthenticationEntity, UUID>{

}
