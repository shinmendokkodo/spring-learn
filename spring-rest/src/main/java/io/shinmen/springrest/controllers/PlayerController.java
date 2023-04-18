package io.shinmen.springrest.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import io.shinmen.springrest.entities.Player;
import io.shinmen.springrest.services.PlayerService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Tennis Player REST API";
    }
    
    @GetMapping
    public List<Player> getAll() {
        return playerService.getAll();
    }

    @GetMapping("/{id}")
    public Player get(@PathVariable("id") Integer id) {
        return playerService.get(id);
    }

    @PostMapping
    public Player post(@RequestBody Player player) {
        return playerService.save(player);
    }

    @PutMapping("/{id}")
    public Player put(@PathVariable("id") Integer id, @RequestBody Player player) {
        return playerService.update(id, player);
    }

    @PatchMapping("/{id}")
    public Player patch(@PathVariable("id") Integer id, @RequestBody Map<String, Object> player) {
        return playerService.patch(id, player);
    }

    @PatchMapping("/{id}/titles")
    public void updateTitles(@PathVariable int id, @RequestBody int titles) {
        playerService.patchTitles(id, titles);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        return playerService.delete(id);
    }

}
