package xyz.subho.clone.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.subho.clone.twitter.security.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
