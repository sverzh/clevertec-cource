package ru.clevertec.checkrunner.repository.JDBC;


import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.exception.StorageException;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.repository.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CardStorageJDBC extends StorageJDBC implements Storage<Card> {
    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGES_DEFAULT = 0;

    public CardStorageJDBC() {
        super();
    }

    public Card findById(int cardNumber) {
        return sqlHelper.transactionalExecute(conn -> {
            Card card;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cardnumbers where cardNumber=?")) {
                ps.setInt(1, cardNumber);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
//                    System.out.println(cardNumber+" - not found in database");
                    card = null;
                } else {
                    card = new Card(rs.getInt("cardnumber"),rs.getInt("discount"));
                }
            }
            return card;
        });
    }

    public void save(Card card) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO cardnumbers (cardnumber) VALUES(?)")) {
                if (findById(card.getNumber()) == null) {
                    ps.setInt(1, card.getNumber());
                    ps.execute();
                }
            }
            return null;
        });
    }

    @Override
    public void update(Card card) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE cardnumbers SET discount=?  WHERE cardnumber = ?")) {
                ps.setInt(1, card.getNumber());
                ps.setInt(2, card.getNumber());
                if (ps.executeUpdate() == 0) {
                    throw new StorageException("Item not find in database");
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

    public List<Card> findAll(String size,String page) {
        List<Card> cardList = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cardnumbers LIMIT ? OFFSET ?")) {

                int pageSize = size != null ? Integer.parseInt(size) : PAGE_SIZE_DEFAULT;
                int pageNumber = page != null ? (Integer.parseInt(page) * pageSize) : PAGES_DEFAULT;
                ps.setInt(1,pageSize);
                ps.setInt(2,pageNumber);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Card card = new Card(rs.getInt("cardnumber"),rs.getInt("discount"));
                    cardList.add(card);
                }
            }
            return null;
        });
        return cardList;
    }
}
