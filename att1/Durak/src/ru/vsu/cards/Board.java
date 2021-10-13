package ru.vsu.cards;

import ru.vsu.data.Suit;

import java.util.ArrayList;

public class Board {

    private class CardCouple {
        public Card attackCard;
        public Card defenseCard = null;

        public CardCouple(Card attackCard) {
            this.attackCard = attackCard;
        }

        public boolean beat(Card card) {
            if (defenseCard != null) {
                return false;
            }
            if (attackCard.compareTo(card, trump) < 0) {
                defenseCard = card;
                return true;
            } else {
                return false;
            }
        }
    }

    private Suit trump;
    private ArrayList<CardCouple> cardCoupleList = new ArrayList<>();

    public Board(Suit trump) {
        this.trump = trump;
    }

    public void addCard(Card card) {
        cardCoupleList.add(new CardCouple(card));
    }
    
    public boolean beat(Card attackCard, Card defenseCard) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.attackCard == attackCard) {
                return couple.beat(defenseCard);
            }
        }
        return false;
    }

    public boolean transfer(Card card) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.attackCard != card) {
                return false;
            }
        }
        addCard(card);
        return true;
    }

    public void takeAllCard(Hand hand) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.defenseCard != null) {
                hand.addCard(couple.defenseCard);
            }
            hand.addCard(couple.attackCard);
        }
    }
    
    public void clear() {
        cardCoupleList.clear();
    }

    public int getSize() {
        return cardCoupleList.size();
    }

    public Suit getTrump() {
        return trump;
    }
}
