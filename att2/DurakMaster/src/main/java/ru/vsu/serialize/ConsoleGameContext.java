package ru.vsu.serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.ConsoleGame;
import ru.vsu.cards.Board;
import ru.vsu.cards.CardDeck;
import ru.vsu.cards.Hand;

import java.io.File;
import java.io.IOException;

public class ConsoleGameContext {

    private int step;
    private CardDeck deck;
    private Board board;
    private Hand playerHand;
    private Hand botHand;

    public ConsoleGameContext(int step, CardDeck deck, Board board, Hand playerHand, Hand botHand) {
        this.step = step;
        this.deck = deck;
        this.board = board;
        this.playerHand = playerHand;
        this.botHand = botHand;
    }

    public ConsoleGameContext() {

    }


    public int getStep() {
        return step;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public Board getBoard() {
        return board;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getBotHand() {
        return botHand;
    }

    public void write(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        if (!fileName.contains(".json")) {
            fileName = fileName+ ".json";
        }
        mapper.writeValue(new File(fileName), this);
    }

    public ConsoleGameContext read(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return mapper.readValue(file, ConsoleGameContext.class);
    }
}
