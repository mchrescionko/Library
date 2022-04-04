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

    @Query("select e from Exchange e join e.receiver re where " +
            "re=:receiver and " +
            "e.exchangeStep=:step")
    List<Exchange> findByReceiver(User receiver, ExchangeStep step);

    @Query("select e from Exchange e join e.sender se where " +
            "se=:sender and " +
            "e.exchangeStep=:step")
    List<Exchange> findBySender(User sender, ExchangeStep step);

    @Query(value = "select e from Exchange where " +
            "e.receiver_id = :receiver and" +
            "e.sender_id = :sender and" +
            "e.receiver_book_id = :book", nativeQuery = true)
    Optional<Exchange> findExchange(Integer sender, Integer receiver, String book);
}
