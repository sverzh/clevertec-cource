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

    public Card get(String cardNumber) {
        return sqlHelper.transactionalExecute(conn -> {
            Card card;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cardnumbers where cardNumber=?")) {
                ps.setString(1, cardNumber);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
//                    System.out.println(cardNumber+" - not found in database");
                    card = null;
                } else {
                    card = new Card(rs.getString("cardnumber"));
                }
            }
            return card;
        });
    }

    public void save(Card card) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cardnumbers (cardnumber) VALUES(?)")) {
                if (get(card.getCardNumber()) == null) {
                    ps.setString(1, card.getCardNumber());
                    ps.execute();
                }
            }
            return null;
        });
    }


    public void delete(String cardnumber) {
        sqlHelper.execute("DELETE FROM cardnumbers i WHERE i.cardnumber=?", ps -> {
            ps.setString(1, cardnumber);
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
                    Card card = new Card(rs.getString("cardnumber"));
                    cardList.add(card);
                }
            }
            return null;
        });
        return cardList;
    }
}
