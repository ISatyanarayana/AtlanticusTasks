package com.transaction.repository;

import com.transaction.entity.Transaction;
import com.transaction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByAccountNumber(int accountNumber);
}
