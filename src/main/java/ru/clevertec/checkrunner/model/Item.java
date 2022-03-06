package ru.clevertec.checkrunner.model;

import java.util.Objects;

public class Item {
    private final int id;
    private String description;
    private double price;
    private boolean offer;

    public Item(int id, String description, double price, boolean offer) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.offer = offer;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id == item.id && Double.compare(item.price, price) == 0 && offer == item.offer && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, offer);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", offer=" + offer +
                '}';
    }
}
