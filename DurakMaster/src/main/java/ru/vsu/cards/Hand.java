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

    public int size() {
        return cardList.size();
    }

    public Card contains(Card card) {
        for (Card compareCard : cardList) {
            if (compareCard.equals(card)) {
                return compareCard;
            }
        }
        return null;
    }

    public Card containsValue(Card card) {
         for (Card compareCard : cardList) {
             if (compareCard.getValue() == card.getValue()) {
                 return compareCard;
             }
         }
         return null;
    }

    public void remove(Card card) {
        cardList.remove(card);
    }

    public Card getCard(int i) {
        return cardList.get(i);
    }

}
