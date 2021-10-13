package ru.vsu;

import ru.vsu.cards.Board;
import ru.vsu.cards.CardDeck;
import ru.vsu.cards.Hand;
import ru.vsu.data.Suit;
import ru.vsu.draw.ConsoleDraw;

public class Main {

    public static void main(String[] args) throws Exception {
        CardDeck deck = new CardDeck();
        Suit trump = deck.getTrump();
        Board board = new Board(trump);
        Hand playerHand = new Hand();
        playerHand.fillFromDeck(deck);
        Hand botHand = new Hand();
        botHand.fillFromDeck(deck);
        ConsoleDraw.drawBotHand();
        System.out.println("Trump = " + board.getTrump() + " (" + board.getTrump().toString().charAt(0) + ")");
        ConsoleDraw.drawPlayerHand(playerHand);
    }
}
