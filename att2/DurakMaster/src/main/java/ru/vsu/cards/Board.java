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

        public boolean ChekToBeat(Card card) {
            if (defenseCard != null) {
                return false;
            }
            Integer compare = attackCard.compareTo(card, trump);
            if (compare == null) {
                return false;
            }
            if (compare < 0) {
                return true;
            }
            return false;
        }

        public void beat(Card card) {
            defenseCard = card;
        }
    }

    private Suit trump;
    private ArrayList<CardCouple> cardCoupleList = new ArrayList<>();

    public Board(Suit trump) {
        this.trump = trump;
    }

    public Board() {

    }

    public void addCard(Card card) {
        cardCoupleList.add(new CardCouple(card));
    }

    public void setTrump(Suit trump) {
        this.trump = trump;
    }

    public void setCardCoupleList(ArrayList<CardCouple> cardCoupleList) {
        this.cardCoupleList = cardCoupleList;
    }
    
    public boolean chekToBeatCard(Card attackCard, Card defenseCard) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.attackCard.equals(attackCard)) {
                boolean beat = couple.ChekToBeat(defenseCard);
                if (beat) {
                    couple.beat(defenseCard);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void beat(Card card, int couple) {
        cardCoupleList.get(couple).defenseCard = card;
    }

    public void beat(Card attackCard, Card defenseCard) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.attackCard.equals(attackCard)) {
                couple.defenseCard = defenseCard;
            }
        }
    }

    public Integer transfer(Hand hand) {
        for (int i = 0; i < hand.size(); i++) {
            if(cardCoupleList.get(0).attackCard.getValue() == hand.getCard(i).getValue()) {
                return hand.getCard(i).getValue();
            }
        }
        return null;
    }

    public boolean contains(Card card) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.attackCard.equals(card)) {
                return true;
            }
        }
        return false;
    }

    public void takeAllCard(Hand hand) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.defenseCard != null) {
                hand.addCard(couple.defenseCard);
            }
            hand.addCard(couple.attackCard);
        }
        this.clear();
    }

    public boolean chekToAddFromHand(Hand hand) {
        for (CardCouple couple : cardCoupleList) {
            for (int i = 0; i < hand.size(); i++) {
                if (couple.defenseCard != null && couple.defenseCard.getValue() == hand.getCard(i).getValue()) {
                    return true;
                }
                if (couple.attackCard.getValue() == hand.getCard(i).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean chekToBeat(Hand hand) {
        for (CardCouple couple : cardCoupleList) {
            for (int i = 0; i < hand.size(); i++) {
                if (couple.ChekToBeat(hand.getCard(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean chekToAddCard(Card card) {
        for (CardCouple couple : cardCoupleList) {
            if (couple.defenseCard != null && couple.defenseCard.getValue() == card.getValue()) {
                return true;
            }
            if (couple.attackCard.getValue() == card.getValue()) {
                return true;
            }
        }
        return false;
    }
    
    public void clear() {
        cardCoupleList.clear();
    }

    public Card getAttackCard(int i) {
        return cardCoupleList.get(i).attackCard;
    }

    public Card getDefenseCard(int i) {
        return cardCoupleList.get(i).defenseCard;
    }

    public int defenseCardSize() {
        for (int i = 0; i < cardCoupleList.size(); i++) {
            if (cardCoupleList.get(i).defenseCard == null) {
                return i;
            }
        }
        return this.size();
    }

    public int size() {
        return cardCoupleList.size();
    }

    public Suit getTrump() {
        return trump;
    }
}
