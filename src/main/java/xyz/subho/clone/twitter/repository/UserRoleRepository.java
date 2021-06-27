package xyz.subho.clone.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.subho.clone.twitter.security.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

}
