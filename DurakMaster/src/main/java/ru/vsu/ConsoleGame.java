package ru.vsu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cards.Board;
import ru.vsu.cards.Card;
import ru.vsu.cards.CardDeck;
import ru.vsu.cards.Hand;
import ru.vsu.data.Suit;
import ru.vsu.draw.ConsoleDraw;
import ru.vsu.serialize.ConsoleGameContext;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleGame {
    private final Logger LOGGER = LoggerFactory.getLogger(ConsoleGame.class);
    private final Scanner scanner = new Scanner(System.in);

    int step = -1;
    private CardDeck deck = new CardDeck();
    private Suit trump;
    private Board board = new Board();
    private Hand playerHand = new Hand();
    private Hand botHand = new Hand();
    private ConsoleDraw draw = new ConsoleDraw();
    private BotLogic logic = new BotLogic();

    private ConsoleGameContext gameContext;

    public void startGame() throws Exception {
        while (true) {
            System.out.println("Если хотите запустить демо версии введите /demo, для загрузки - /load, для начала новой игры - /game");
            String startCommand = scanner.nextLine();
            if (startCommand.equals("/demo")) {
                LOGGER.info("Playing demo version");
                demo();
                return;
            }
            if (startCommand.equals("/load")) {
                LOGGER.info("Loading the game");
                load();
                break;
            } else if (startCommand.equals("/game")) {
                LOGGER.info("The game was started");
                deck.fill();
                trump = deck.getTrump();
                playerHand.fillFromDeck(deck);
                botHand.fillFromDeck(deck);
                board.setTrump(trump);
                break;
            }
            else {
                System.out.println("Неверная команда");
            }
        }

        while (playerHand.size() != 0 && botHand.size() != 0) {
            draw.drawField(playerHand, trump);
            System.out.println("Если хотите сохранить игру введите /save");
            String saveCommand = scanner.nextLine();
            if (saveCommand.equals("/save")) {
                save();
            }
            step = step * -1;
            if(step == 1) {
                System.out.println("Ваш ход");
                playerStep();
            } else {
                System.out.println("Ход противника");
                botStep();
            }

        }
        if (playerHand.size() == 0) {
            LOGGER.info("Player won");
            System.out.println("Вы выйграли!!!");
        } else {
            LOGGER.info("Player lost");
            System.out.println("Вы проиграли((((((");
        }
    }

    private void playerStep() throws Exception {
        while (true) {
            playCards();
            if (!logic.botBeatCards(board, botHand)) {
                LOGGER.info("Player step: bot takes cards");
                System.out.println("Противник взял карты с доски");
                step = step * -1;
                playerHand.fillFromDeck(deck);
                botHand.fillFromDeck(deck);
                break;
            } else {
                System.out.println("Карты на доске:");
                draw.drawBoard(board, step);
            }
            if (!chekAddCards()) {
                LOGGER.info("Player step: bot beats cards");
                playerHand.fillFromDeck(deck);
                botHand.fillFromDeck(deck);
                board.clear();
                break;
            }
        }
    }

    private void botStep() throws Exception {
        logic.botPlayCards(board, botHand);
        System.out.println("Карты на доске:");
        draw.drawBoard(board, step);
        System.out.println("Ваши карты:");
        draw.drawPlayerHand(playerHand);
        draw.drawTrump(trump);
        Integer transferValue = board.transfer(playerHand);
        if (transferValue != null) {
                System.out.println("Хотите перевести? 1 - да, 0 - нет");
                String answer = scanner.nextLine();
                if (answer.equals("1")) {
                    LOGGER.info("Bot step: player transfer cards");
                    transferCards(transferValue);
                    return;
                }
        }
        if (board.chekToBeat(playerHand)) {
            while (true) {
                System.out.println("Хотите бить карты? 1 - да, 0 - нет");
                String answer = scanner.nextLine();
                if (answer.equals("1")) {
                    playerBeatCards();
                    LOGGER.info("Bot step: player beats cards");
                    break;
                } else if (answer.equals("0")) {
                    LOGGER.info("Bot step: player takes cards");
                    step = step * -1;
                    board.takeAllCard(playerHand);
                    break;
                } else {
                    System.out.println("Неправильная цифра");
                }
            }
        } else {
            LOGGER.info("Bot step: player takes cards");
            System.out.println("Вы берете карты");
            board.takeAllCard(playerHand);
            step = step * -1;
        }
        playerHand.fillFromDeck(deck);
        botHand.fillFromDeck(deck);
        board.clear();
    }

    private void playCards() {
        System.out.println("Разыграйте карты");
        String cardStr = scanner.nextLine();
        Card card = fromStr(cardStr);
        if (card == null) {
            playCards();
            return;
        }
        Card containCard = playerHand.contains(card);
        if (containCard == null) {
            System.out.println("Такой карты в руке нет");
            playCards();
            return;
        }
        if (board.size() != 0 && !board.chekToAddCard(card)) {
            System.out.println("Эту карту нельзя разыграть");
            playCards();
            return;
        }
        playerHand.remove(containCard);
        board.addCard(card);

        if (chekAddCards()) {
            playCards();
        }
    }

    private void transferCards(int value) throws Exception {
        System.out.println("Сыграйте карту");
        String cardStr = scanner.nextLine();
        Card card = fromStr(cardStr);
        if (card == null) {
            transferCards(value);
            return;
        }
        Card containCard = playerHand.contains(card);
        if (containCard == null) {
            System.out.println("Такой карты в руке нет");
            transferCards(value);
            return;
        }
        if (board.getAttackCard(0).getValue() != value) {
            System.out.println("Этой картой нельзя перевести");
            transferCards(value);
            return;
        }
        step = step * -1;
        board.addCard(containCard);
        playerHand.remove(containCard);
        if (logic.botBeatCards(board, botHand)) {
            System.out.println("Карты на доске");
            draw.drawBoard(board, step);
            if (chekAddCards()) {
                playerStep();
            }
        } else {
            System.out.println("Противник взял карты с доски");
            step = step * -1;
            playerHand.fillFromDeck(deck);
            botHand.fillFromDeck(deck);
            board.clear();
        }
    }

    private boolean chekAddCards() {
        if (board.chekToAddFromHand(playerHand)) {
            System.out.println("Ваши карты:");
            draw.drawPlayerHand(playerHand);
            draw.drawTrump(trump);
            while (true) {
                System.out.println("Разыграть еще карты? 1 - да, 0 - нет");
                String answer = scanner.nextLine();
                if (answer.equals("1")) {
                    return true;
                } else if (answer.equals("0")) {
                    return false;
                } else {
                    System.out.println("Неправильная цифра");
                }
            }
        }
        return false;
    }


    private void playerBeatCards() {
        System.out.println("Какую карту будете отбивать");
        String attackStr = scanner.nextLine();
        Card attackCard = fromStr(attackStr);
        if (attackCard == null) {
            playerBeatCards();
            return;
        }
        if (!board.contains(attackCard)) {
            System.out.println("На столе нет такой карты");
            playerBeatCards();
        }

        System.out.println("Сыграйте карту");
        String defenseStr = scanner.nextLine();
        Card defenseCard = fromStr(defenseStr);
        if (defenseCard == null) {
            playerBeatCards();
            return;
        }
        Card containCard = playerHand.contains(defenseCard);
        if (containCard == null) {
            System.out.println("Такой карты в руке нет");
            playerBeatCards();
            return;
        }
        if (board.chekToBeatCard(attackCard, defenseCard)) {
            playerHand.remove(containCard);
        } else {
            System.out.println("Этой картой нельзя побить");
            playerBeatCards();
        }
    }

    private void demo() throws Exception {
        deck.fill();
        trump = deck.getTrump();
        playerHand.fillFromDeck(deck);
        botHand.fillFromDeck(deck);

        while (playerHand.size() != 0 && botHand.size() != 0) {
            draw.drawField(playerHand, trump);
            step = step * -1;
            if(step == 1) {
                pause();
                logic.botPlayCards(board, playerHand);
                System.out.println("Карты на доске:");
                draw.drawBoard(board, step);
                pause();
                if (!logic.botBeatCards(board, botHand)) {
                    System.out.println("Противник взял карты с доски");
                    step = step * -1;
                } else {
                    System.out.println("Карты на доске:");
                    draw.drawBoard(board, step);
                }
                playerHand.fillFromDeck(deck);
                botHand.fillFromDeck(deck);
                board.clear();
                pause();
            } else {
                pause();
                logic.botPlayCards(board, botHand);
                System.out.println("Карты на доске:");
                draw.drawBoard(board, step);
                pause();
                if (logic.botBeatCards(board, playerHand)) {
                    System.out.println("Противник взял карты с доски");
                    step = step * -1;
                } else {
                    System.out.println("Карты на доске:");
                    draw.drawBoard(board, step);
                }
                playerHand.fillFromDeck(deck);
                botHand.fillFromDeck(deck);
                board.clear();
                pause();
            }

        }
    }

    private void pause() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void save() throws IOException {
        System.out.println("Назовите сохранение");
        String fileName = scanner.nextLine();
        gameContext = new ConsoleGameContext(step, deck, board, playerHand, botHand);
        gameContext.write(fileName);
    }

    private void load() throws IOException {
        File[] jsonFiles = new File(".").listFiles((dir, name) -> name.endsWith(".json"));
        System.out.println("Сохранения:");
        for (File file : jsonFiles) {
            System.out.println(file.getName());
        }
        while (true) {
            System.out.println("Выберете сохранение");
            String fileName = scanner.nextLine();
            for (File file : jsonFiles) {
                if (file.getName().equals(fileName)) {
                    gameContext = new ConsoleGameContext();
                    gameContext = gameContext.read(file);
                    step = gameContext.getStep();
                    deck = gameContext.getDeck();
                    trump = deck.getTrump();
                    board = gameContext.getBoard();
                    playerHand = gameContext.getPlayerHand();
                    botHand = gameContext.getBotHand();
                    return;
                }
            }
            System.out.println("Неверное название сохранения");
        }
    }

    private Card fromStr(String cardStr) {
        String[] str = cardStr.split(" ");
        int value;
        Suit suit;
        if (str.length == 2) {
            switch (str[1]) {
                case "C": suit = Suit.CLUBS;
                    break;
                case "D": suit = Suit.DIAMONDS;
                    break;
                case "S": suit = Suit.SPADES;
                    break;
                case "H": suit = Suit.HEARTS;
                    break;
                default: {
                    System.out.println("Неправильно введена карта");
                    return null;
                }
            }
            value = Integer.parseInt(str[0]);
            if (value > 15 || value < 6) {
                System.out.println("Неправильно введена карта");
                return null;
            }
        } else {
            System.out.println("Неправильно введена карта");
            return null;
        }
        return new Card(value, suit);
    }


}
