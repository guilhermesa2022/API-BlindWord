package com.hotTable.API_BlindWord.logic;

import com.hotTable.API_BlindWord.repository.WordRepository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameSession {

    private final Map<String, Game> mapOfGame;
    private final Map<String, WebSocketSession> mapOfWebSocketSession;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public GameSession(WebSocketSession sessionPlayer1,
                       WebSocketSession sessionPlayer2,
                       WordRepository wordRepository) {
        mapOfGame = new HashMap<>();
        mapOfWebSocketSession = new HashMap<>();

        final Game game1 = new Game(new ManegerWord(wordRepository));
        final Game game2 = new Game(new ManegerWord(wordRepository));

        mapOfGame.put(sessionPlayer1.getId(), game1);
        mapOfGame.put(sessionPlayer2.getId(), game2);
        mapOfWebSocketSession.put(sessionPlayer1.getId(), sessionPlayer1);
        mapOfWebSocketSession.put(sessionPlayer2.getId(), sessionPlayer2);
    }

    public void makeAAttenmpt(String playerId, String wordsGuess) throws IOException {
        Game game = mapOfGame.get(playerId);

        ValidatePlay validatePlay = game.makeAAttenmpt(wordsGuess);

        for (WebSocketSession session : mapOfWebSocketSession.values()) {
            sendTo(session, validatePlay);
        }
    }

    public void sendTo(WebSocketSession session, Object message) throws IOException {
        if (session == null || !session.isOpen()) {
            return;
        }

        String jsonMessage = objectMapper.writeValueAsString(message);

        synchronized (session) {
            session.sendMessage(new TextMessage(jsonMessage));
        }
    }



}
