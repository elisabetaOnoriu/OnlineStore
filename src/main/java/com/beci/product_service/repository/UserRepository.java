package com.beci.product_service.repository;

// UserRepository

import com.beci.product_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("{'roles': {$in: [?0]}}")
    List<User> findByRole(String role);

    @Query("{'enabled': true}")
    Page<User> findActiveUsers(Pageable pageable);

    @Query("{'firstName': {$regex: ?0, $options: 'i'}, 'lastName': {$regex: ?1, $options: 'i'}}")
    List<User> findByFirstNameAndLastNameContainingIgnoreCase(String firstName, String lastName);
}