package ru.vsu.cards;

import ru.vsu.data.Suit;

import java.util.Random;

public class CardDeck {

    private LinkedCard head = null;
    private LinkedCard tail = null;
    private Suit trump;
    private int size = 36;

    /*public CardDeck() {
        Card[] deck = new Card[36];
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 4; j++) {
                deck[i * 4 + j] = new Card(i + 6, Suit.values()[j]);
            }
        }

        Random random = new Random();
        for(int i = 0; i < 36; i++) {
            int randomCard = random.nextInt(36);
            Card card = deck[i];
            deck[i] = deck[randomCard];
            deck[randomCard] = card;
        }

        for(int i = 0; i < 36; i++) {
            head = new LinkedCard(deck[i], head);
            if (i == 0) {
                tail = head;
            }
        }
        trump = tail.card.getSuit();
    }*/

    public void fill() {
        Card[] deck = new Card[36];
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 4; j++) {
                deck[i * 4 + j] = new Card(i + 6, Suit.values()[j]);
            }
        }

        Random random = new Random();
        for(int i = 0; i < 36; i++) {
            int randomCard = random.nextInt(36);
            Card card = deck[i];
            deck[i] = deck[randomCard];
            deck[randomCard] = card;
        }

        for(int i = 0; i < 36; i++) {
            head = new LinkedCard(deck[i], head);
            if (i == 0) {
                tail = head;
            }
        }
        trump = tail.card.getSuit();
    }

    public Card takeCard() throws Exception {
        if (size == 0) {
            throw new Exception("Stack is empty");
        }
        Card card = head.card;
        head = head.next;
        size--;
        return card;
    }

    public Suit getTrump() {
        return trump;
    }

    public int getSize() {
        return size;
    }
}
