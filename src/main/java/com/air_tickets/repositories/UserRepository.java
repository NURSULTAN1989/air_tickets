package com.air_tickets.repositories;

import com.air_tickets.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByLogin(String login);
    List<Users> findUsersByRoles(String roles);

}
