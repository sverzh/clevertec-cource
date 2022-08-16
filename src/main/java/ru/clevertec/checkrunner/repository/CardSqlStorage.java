package ru.clevertec.checkrunner.repository;


import ru.clevertec.checkrunner.exception.StorageException;
import ru.clevertec.checkrunner.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CardSqlStorage extends SqlStorage {

    public CardSqlStorage() {
        super();
    }

    public Card get(int cardNumber) {
        return sqlHelper.transactionalExecute(conn -> {
            Card card;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cardnumbers where cardNumber=?")) {
                ps.setInt(1, cardNumber);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
//                    System.out.println(cardNumber+" - not found in database");
                    card = null;
                } else {
                    card = new Card(rs.getInt("cardnumber"));
                }
            }
            return card;
        });
    }

    public void add(Card card) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cardnumbers (cardnumber) VALUES(?)")) {
                if (get(card.getCardNumber()) == null) {
                    ps.setInt(1, card.getCardNumber());
                    ps.execute();
                }
            }
            return null;
        });
    }


    public void delete(int cardnumber) {
        sqlHelper.execute("DELETE FROM cardnumbers i WHERE i.cardnumber=?", ps -> {
            ps.setInt(1, cardnumber);
            if (ps.executeUpdate() == 0) {
                throw new StorageException("Card -" + cardnumber + " not found in database");
            }
            return null;
        });
    }

    public List<Card> findAll() {
        List<Card> cardList = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cardnumbers")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Card card = new Card(rs.getInt("cardnumber"));
                    cardList.add(card);
                }
            }
            return null;
        });
        return cardList;
    }
}
