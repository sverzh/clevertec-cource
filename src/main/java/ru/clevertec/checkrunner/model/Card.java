package ru.clevertec.checkrunner.model;

public class Card {
    private final String cardNumber;

    private int discount=5;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (discount != card.discount) return false;
        return cardNumber != null ? cardNumber.equals(card.cardNumber) : card.cardNumber == null;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + discount;
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", discount=" + discount +
                '}';
    }
}
