package ru.vsu;

import ru.vsu.cards.Board;
import ru.vsu.cards.Hand;

import java.util.Random;

public class BotLogic {
    public boolean botBeatCards(Board board, Hand botHand) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < botHand.size(); j++) {
                Integer compare = board.getAttackCard(i).compareTo(botHand.getCard(j), board.getTrump());
                if (compare != null && compare < 0) {
                    board.beat(botHand.getCard(j), i);
                    botHand.remove(botHand.getCard(j));
                    break;
                }
            }
        }
        if (board.size() > board.defenseCardSize()) {
            board.takeAllCard(botHand);
            return false;
        }
        return true;
    }

    public void botPlayCards(Board board, Hand botHand) {
        Random rnd = new Random();
        int random = rnd.nextInt(botHand.size());
        board.addCard(botHand.getCard(random));
        botHand.remove(botHand.getCard(random));
    }
}
