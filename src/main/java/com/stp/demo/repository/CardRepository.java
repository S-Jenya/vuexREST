package com.stp.demo.repository;

import com.stp.demo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select user.cards from User user where user.idUser = ?1")
    List<Card> findCardByUserIdCustomQuery(Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Card card set card.headline = ?1 where card.idCard = ?2")
    void updCard(String newName, Long cardId);

    @Query("select card from Card card where card.user.idUser = ?1")
    List<Card> findCardCustomQuery(Long userId);

    @Query("select card from Card card where card.idCard = ?1")
    Card findCardByIdCustomQuery(Long idCard);
}
