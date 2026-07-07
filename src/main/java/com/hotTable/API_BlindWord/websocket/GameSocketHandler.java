package com.hotTable.API_BlindWord.websocket;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private final Queue<String> QueueOfPlayes = new LinkedList<>();
    //private final Map<String, ManagerOfGames> Game = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        players.put(session.getId(), session);
        //QueueOfPlayes.add(session.getId());

        System.out.println("Jogador ligado: " + session.getId());

        sendTo(session.getId(), """
            {
              "type": "CONNECTED"
            }
            """);


        if(QueueOfPlayes.isEmpty()){
            sendTo(session.getId(), """
            {
              "ation": "await"
            }
            """);
            QueueOfPlayes.add(session.getId());
        }else{
            
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String playerId = session.getId();
        String payload = message.getPayload();

        System.out.println("Mensagem de " + playerId + ": " + payload);

        /*
         * Isto é o teu receive.
         * Tudo o que o cliente enviar cai aqui.
         */

        // Exemplo: responder só ao jogador que enviou
        sendTo(playerId, """
            {
              "type": "SERVER_RESPONSE",
              "message": "Recebi a tua mensagem"
            }
            """);

        // Exemplo: enviar para todos os jogadores
        broadcast("""
            {
              "type": "PLAYER_MESSAGE",
              "from": "%s",
              "data": %s
            }
            """.formatted(playerId, payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        players.remove(session.getId());

        System.out.println("Jogador saiu: " + session.getId());
    }

    public void sendTo(String playerId, String message) throws IOException {
        WebSocketSession session = players.get(playerId);

        if (session == null || !session.isOpen()) {
            return;
        }

        synchronized (session) {
            session.sendMessage(new TextMessage(message));
        }
    }

    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : players.values()) {
            if (session.isOpen()) {
                synchronized (session) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    public WebSocketSession getPlayer(String playerId) {
        return players.get(playerId);
    }

    public Map<String, WebSocketSession> getPlayers() {
        return players;
    }
}


/*
package com.hotTable.API_BlindWord.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> players = new ConcurrentHashMap<>();
    private final Map<String, Long> lastPong = new ConcurrentHashMap<>();

    private static final long TIMEOUT_MS = 15000; // 15 segundos sem resposta = morto

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String playerId = session.getId();

        players.put(playerId, session);
        lastPong.put(playerId, System.currentTimeMillis());

        System.out.println("Jogador ligado: " + playerId);

        sendTo(playerId, """
            {
              "type": "CONNECTED",
              "playerId": "%s"
            }
            """.formatted(playerId));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String playerId = session.getId();
        String payload = message.getPayload();

        // Qualquer mensagem recebida prova que o cliente ainda está vivo
        lastPong.put(playerId, System.currentTimeMillis());

        System.out.println("Mensagem de " + playerId + ": " + payload);

        // Cliente respondeu ao PING
        if (payload.contains("\"type\":\"PONG\"") || payload.contains("\"type\": \"PONG\"")) {
            return;
        }

        // Aqui fica a lógica normal do teu jogo
        broadcast("""
            {
              "type": "PLAYER_MESSAGE",
              "from": "%s",
              "data": %s
            }
            """.formatted(playerId, payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String playerId = session.getId();

        players.remove(playerId);
        lastPong.remove(playerId);

        System.out.println("Jogador saiu: " + playerId);
    }

    @Scheduled(fixedRate = 5000)
    public void heartbeatCheck() {
        long now = System.currentTimeMillis();

        for (String playerId : players.keySet()) {
            WebSocketSession session = players.get(playerId);

            if (session == null || !session.isOpen()) {
                players.remove(playerId);
                lastPong.remove(playerId);
                continue;
            }

            long lastSeen = lastPong.getOrDefault(playerId, 0L);

            if (now - lastSeen > TIMEOUT_MS) {
                System.out.println("Jogador morto por timeout: " + playerId);

                try {
                    session.close(CloseStatus.SESSION_NOT_RELIABLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                players.remove(playerId);
                lastPong.remove(playerId);

                continue;
            }

            try {
                sendTo(playerId, """
                    {
                      "type": "PING"
                    }
                    """);
            } catch (IOException e) {
                players.remove(playerId);
                lastPong.remove(playerId);
            }
        }
    }

    public void sendTo(String playerId, String message) throws IOException {
        WebSocketSession session = players.get(playerId);

        if (session == null || !session.isOpen()) {
            return;
        }

        synchronized (session) {
            session.sendMessage(new TextMessage(message));
        }
    }

    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : players.values()) {
            if (session.isOpen()) {
                synchronized (session) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    public WebSocketSession getPlayer(String playerId) {
        return players.get(playerId);
    }

    public Map<String, WebSocketSession> getPlayers() {
        return players;
    }
}
* */