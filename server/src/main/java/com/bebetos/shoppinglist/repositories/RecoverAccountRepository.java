package com.bebetos.shoppinglist.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bebetos.shoppinglist.models.RecoverAccount;

@Repository
public interface RecoverAccountRepository extends JpaRepository<RecoverAccount, Long>  {

    Optional<RecoverAccount> findById(Long id);

    Optional<RecoverAccount> findByEmailAndToken(String email, String token);

}