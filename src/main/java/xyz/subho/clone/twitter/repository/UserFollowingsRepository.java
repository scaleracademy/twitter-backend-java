package xyz.subho.clone.twitter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.subho.clone.twitter.entity.UserFollowings;

public interface UserFollowingsRepository extends JpaRepository<UserFollowings, UUID> {

}
