package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional(readOnly=true)
@Repository
public interface UserRepository extends JpaRepository <User, Integer>{
    public Optional<User> findByEmail (String email);
    public Optional<User> findById (String id);
    public List<User> findByBooks(Book book);
}
