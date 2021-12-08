package ru.vsu.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.vsu.data.Suit;

public class Card {
    private int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Card() {

    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public Integer compareTo(Card card, Suit trump) {
        if(suit.equals(trump) && !card.getSuit().equals(trump)) {
            return 1;
        }
        if(!suit.equals(trump) && card.getSuit().equals(trump)) {
            return -1;
        }
        if (suit.equals(card.getSuit())) {
            return value - card.getValue();
        }
        return null;
    }

    public boolean equals(Card card) {
        if (this == card) {
            return true;
        }
        if (card == null) {
            return false;
        }
        return value == card.value && suit == card.suit;
    }

    public String toString() {
        return value + " " + suit.toString().charAt(0);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}