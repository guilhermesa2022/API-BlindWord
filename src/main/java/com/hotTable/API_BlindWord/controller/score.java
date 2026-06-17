package com.hotTable.API_BlindWord.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class score {

    @GetMapping("/Leaderboard")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> Leaderboard() {

        return List.of("Gui - 100", "Ana - 90");

    }
}
