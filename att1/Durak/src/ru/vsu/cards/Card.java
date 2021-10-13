package ru.vsu.cards;

import ru.vsu.data.Suit;

public class Card {
    private Suit suit;
    private int value;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public int compareTo(Card card, Suit trump) {
        if(suit.equals(trump) && !card.getSuit().equals(trump)) {
            return 1;
        }
        if(!suit.equals(trump) && card.getSuit().equals(trump)) {
            return -1;
        }
        return value - card.getValue();
    }

    public String toString() {
        return String.valueOf(value) + suit.toString().charAt(0);
    }

}