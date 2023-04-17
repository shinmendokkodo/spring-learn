package io.shinmen.springrest.bootstrap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.shinmen.springrest.entities.Player;
import io.shinmen.springrest.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoad implements CommandLineRunner {

    private final PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() throws ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Player p1 = Player.builder().name("Djokovic").nationality("Serbia").birthDate(formatter.parse("22-05-1987")).titles(81).build();
        Player p2 = Player.builder().name("Monfils").nationality("France").birthDate(formatter.parse("01-09-1986")).titles(10).build();
        Player p3 = Player.builder().name("Isner").nationality("USA").birthDate(formatter.parse("26-04-1985")).titles(15).build();

        playerRepository.saveAll(List.of(p1, p2, p3));

    }
    
}
