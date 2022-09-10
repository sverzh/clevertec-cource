package ru.clevertec.checkrunner.repository.JDBC;

import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.exception.StorageException;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ItemStorageJDBC extends StorageJDBC implements Storage<Item> {
    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGES_DEFAULT = 0;

    public ItemStorageJDBC() {
        super();
    }

    @Override
    public Item findById(int id) {
        return sqlHelper.transactionalExecute(conn -> {
            Item item;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM items where id=?")) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new StorageException("Item with id-"+id+" not found in database");
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
                ps.setBoolean(4, item.isOffer());
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public void update(Item item) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE items SET name = ?, price=?, offer=? WHERE id = ?")) {
                ps.setString(1, item.getName());
                int id = item.getId();
                ps.setDouble(2, item.getPrice());
                ps.setBoolean(3, item.isOffer());
                ps.setInt(4, id);
                if (ps.executeUpdate() == 0) {
                    throw new StorageException("Item not find in database");
                }
            }
            return null;
        });
    }


    @Override
    public void delete(int id) {
        sqlHelper.execute("DELETE FROM items i WHERE i.id=?", ps -> {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new StorageException("Item with id-"+id+" not found in database");
            }
            return null;
        });
    }

    @Override
    public List<Item> findAll(String size, String page) {
        Map<Integer, Item> items = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM items LIMIT ? OFFSET ?")) {
                int pageSize = size != null ? Integer.parseInt(size) : PAGE_SIZE_DEFAULT;
                int pageNumber = page != null ? (Integer.parseInt(page) * pageSize) : PAGES_DEFAULT;
                ps.setInt(1,pageSize);
                ps.setInt(2,pageNumber);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Item item = items.get(id);
                    if (item == null) {
                        item = new Item(id, rs.getString("name"), rs.getDouble("price"), rs.getBoolean("offer"));
                        items.put(id, item);
                    }
                }
            }
            return null;
        });
        return new ArrayList<>(items.values());
    }
}
