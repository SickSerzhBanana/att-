package ru.vsu.cards;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cardList = new ArrayList<>();

    public void fillFromDeck(CardDeck deck) throws Exception {
        if (cardList.size() >= 6) {
            return;
        }
        while (cardList.size() < 6 && deck.getSize() > 0) {
            addCard(deck.takeCard());
        }
    }

    public void addCard(Card card) {
        cardList.add(card);
    }

    public int getSize() {
        return cardList.size();
    }

    public boolean contains(Card card) {
        return cardList.contains(card);
    }

    public Card getCard(int i) {
        return cardList.get(i);
    }

}
