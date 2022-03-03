package com.example.library.repository;

import com.example.library.model.Exchange;

import com.example.library.model.ExchangeStep;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    //tutorial repozytoriów springowych (Java Persistence)

    @Query("select e from Exchange e join e.receiver re where " +
            "re=:receiver and " +
            "e.exchangeStep=:step")
    public List<Exchange> findByReceiver(User receiver, ExchangeStep step);

    @Query("select e from Exchange e join e.sender se where " +
            "se=:sender and " +
            "e.exchangeStep=:step")
    public List<Exchange> findBySender(User sender, ExchangeStep step);
    public Optional<Exchange> findById(Integer id);
    public void deleteById(String id);
}
