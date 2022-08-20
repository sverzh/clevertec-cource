package ru.clevertec.checkrunner.model;

public class Card {

    private final int cardNumber;
    private int discount=5;

    public Card(int cardnumber) {
        this.cardNumber=cardnumber;
    }


    public int getCardNumber() {
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

        if (cardNumber != card.cardNumber) return false;
        return discount == card.discount;
    }

    @Override
    public int hashCode() {
        int result = cardNumber;
        result = 31 * result + discount;
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber=" + cardNumber +
                ", discount=" + discount +
                '}';
    }
}
