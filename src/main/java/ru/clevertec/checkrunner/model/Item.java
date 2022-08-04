package ru.clevertec.checkrunner.model;

import java.util.Objects;

public class Item {
    private final int id;
    private String name;
    private double price;
    private boolean offer;

    public Item(int id, String name, double price, boolean offer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.offer = offer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String description) {
        this.name = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
        return id == item.id && Double.compare(item.price, price) == 0 && offer == item.offer && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, offer);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + name + '\'' +
                ", price=" + price +
                ", offer=" + offer +
                '}';
    }
}
