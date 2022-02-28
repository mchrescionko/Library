package com.example.library.repository;

import com.example.library.model.Exchange;

import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    public List<Exchange> findByReceiver(User receiver);
    public List<Exchange> findBySender(User sender);
    public Optional<Exchange> findById(Integer id);
    public void deleteById(String id);
}
