package com.hotTable.API_BlindWord.logic;

import com.hotTable.API_BlindWord.repository.WordRepository;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class GameSession {

    public GameSession(WebSocketSession sessionPlayer1, WebSocketSession sessionPlayer2){
        Map<String, Game> mapOfGame = new HashMap<>();

        final Game game1 = new Game(new ManegerWord(new WordRepository()));

    }


}
