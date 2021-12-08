package ru.vsu.draw;

import ru.vsu.cards.Board;
import ru.vsu.cards.Hand;
import ru.vsu.data.Suit;

public class ConsoleDraw {
    public final int CARD_IN_ROW = 9;

    public void drawField(Hand playerHand, Suit trump) {
        System.out.println("Поле");
        drawBotHand();
        drawTrump(trump);
        drawPlayerHand(playerHand);
    }

    public void drawPlayerHand(Hand hand) {
        int size = hand.size();
        int lastRow = size % CARD_IN_ROW;
        int numberRow = (size - lastRow) / 9;
        numberRow++;
        for (int i = 0; i < numberRow; i++) {
            if (i == numberRow - 1) {
                for (int j = 0; j < lastRow; j++) {
                    System.out.print(" __  ");
                }
                System.out.print("\n");
                for (int j = 0; j < lastRow; j++) {
                    System.out.print("|" + hand.getCard(j).getValue());
                    if (String.valueOf(hand.getCard(j).getValue()).length() == 1) {
                        System.out.print(" | ");
                    } else {
                        System.out.print("| ");
                    }
                }
                System.out.print("\n");
                for (int j = 0; j < lastRow; j++) {
                    System.out.print("| " + hand.getCard(j).getSuit().toString().charAt(0) + "| ");
                }
                System.out.print("\n");
                for (int j = 0; j < lastRow; j++) {
                    System.out.print(" --  ");
                }
            } else {
                for (int j = 0; j < 6; j++) {
                    System.out.print(" __  ");
                }
                System.out.print("\n");
                for (int j = 0; j < 6; j++) {
                    System.out.print("|" + hand.getCard(j).getValue());
                    if (String.valueOf(hand.getCard(j).getValue()).length() == 1) {
                        System.out.print(" | ");
                    } else {
                        System.out.print("| ");
                    }
                }
                System.out.print("\n");
                for (int j = 0; j < 6; j++) {
                    System.out.print("| " + hand.getCard(j).getSuit().toString().charAt(0) + "| ");
                }
                System.out.print("\n");
                for (int j = 0; j < 6; j++) {
                    System.out.print(" --  ");
                }
            }
            System.out.print("\n");

        }
    }

    public void drawBotHand() {
        for (int j = 0; j <6; j++) {
            System.out.print(" __  ");
        }
        System.out.print("\n");
        for (int j = 0; j < 6; j++) {
            System.out.print("|? | ");
        }
        System.out.print("\n");
        for (int j = 0; j < 6; j++) {
            System.out.print("| ?| ");
        }
        System.out.print("\n");
        for (int j = 0; j < 6; j++) {
            System.out.print(" --  ");
        }
        System.out.print("\n");
    }

    public void drawBoard(Board board, int step) {
        if (step == 1) {
            drawDefenseCards(board);
            drawAttackCards(board);
        } else {
            drawAttackCards(board);
            drawDefenseCards(board);
        }
    }

    public void drawAttackCards(Board board) {
        if (board.size() == 0) {
            return;
        }
        for (int j = 0; j < board.size(); j++) {
            System.out.print(" __  ");
        }
        System.out.print("\n");
        for (int j = 0; j < board.size(); j++) {
            System.out.print("|" + board.getAttackCard(j).getValue());
            if (String.valueOf(board.getAttackCard(j).getValue()).length() == 1) {
                System.out.print(" | ");
            } else {
                System.out.print("| ");
            }
        }
        System.out.print("\n");
        for (int j = 0; j < board.size(); j++) {
            System.out.print("| " + board.getAttackCard(j).getSuit().toString().charAt(0) + "| ");
        }
        System.out.print("\n");
        for (int j = 0; j < board.size(); j++) {
            System.out.print(" --  ");
        }
        System.out.print("\n");
    }

    public void drawDefenseCards(Board board) {
        int size = board.defenseCardSize();
        if (size == 0) {
            return;
        }
        for (int j = 0; j < size; j++) {
            System.out.print(" __  ");
        }
        System.out.print("\n");
        for (int j = 0; j < size; j++) {
            System.out.print("|" + board.getDefenseCard(j).getValue());
            if (String.valueOf(board.getDefenseCard(j).getValue()).length() == 1) {
                System.out.print(" | ");
            } else {
                System.out.print("| ");
            }
        }
        System.out.print("\n");
        for (int j = 0; j < size; j++) {
            System.out.print("| " + board.getDefenseCard(j).getSuit().toString().charAt(0) + "| ");
        }
        System.out.print("\n");
        for (int j = 0; j < size; j++) {
            System.out.print(" --  ");
        }
        System.out.print("\n");
    }

    public void drawTrump(Suit trump) {
        System.out.println("Trump = " + trump + " (" + trump.toString().charAt(0) + ")");
    }
}
