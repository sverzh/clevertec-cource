package ru.clevertec.checkrunner.repository;


import ru.clevertec.checkrunner.model.Card;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardNumberSqlStorage extends SqlStorage {

    public CardNumberSqlStorage() {
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
                }
                else {
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
}
