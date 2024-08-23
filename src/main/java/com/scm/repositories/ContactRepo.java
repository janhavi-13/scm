package com.scm.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends MongoRepository<Contact, String> {

    // Find contacts by user
    List<Contact> findByUser(User user);

    // Custom query method using MongoDB query syntax
    @Query("{ 'user.userId': ?0 }")
    List<Contact> findByUserId(@Param("userId") String userId);

    List<Contact> findByUserAndNameContaining(User user, String namekeyword);

    List<Contact> findByUserAndEmailContaining(User user, String emailkeyword);

    List<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword);

}
