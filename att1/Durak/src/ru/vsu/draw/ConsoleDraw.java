package ru.vsu.draw;

import ru.vsu.cards.Card;
import ru.vsu.cards.Hand;

public class ConsoleDraw {
    public static final int CARD_IN_ROW = 9;
    public static void drawPlayerHand(Hand hand) {
        int size = hand.getSize();
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
                System.out.print("\n");
            } else {

            }

        }
    }

    public static void drawBotHand() {
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
}
