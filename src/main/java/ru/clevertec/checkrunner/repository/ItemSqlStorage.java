package ru.clevertec.checkrunner.repository;

import ru.clevertec.checkrunner.model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItemSqlStorage extends SqlStorage implements Storage{

    public ItemSqlStorage() {
        super();
    }

    @Override
    public Item get(int id) {
            return sqlHelper.transactionalExecute(conn -> {
                Item item;
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM items where id=?")) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
//                        System.out.println(id+" - not found in database");
                    }
                    item = new Item(id, rs.getString("name"), rs.getDouble("price"), rs.getBoolean("offer"));
                }

                return item;
            });

        }


    @Override
    public void save(Item item) {
            sqlHelper.transactionalExecute(conn -> {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO items (id, name, price, offer) VALUES(?,?,?,?)")) {
                    ps.setInt(1, item.getId());
                    ps.setString(2, item.getName());
                    ps.setDouble(3, item.getPrice());
                    ps.setBoolean(4,item.isOffer());
                    ps.execute();
                }
                return null;
            });
        }

    }

