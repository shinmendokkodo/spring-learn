package io.shinmen.springrest.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import io.shinmen.springrest.exceptions.PlayerNotFoundException;
import org.springframework.stereotype.Service;

import io.shinmen.springrest.entities.Player;
import io.shinmen.springrest.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    
    private final PlayerRepository playerRepository;

	public List<Player> getAll() {
		return playerRepository.findAll();
	}
	
	public Player get(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id "+ id + " not found."));
    }
	
	public Player save(Player player) {
		return playerRepository.save(player);
	}

    public Player update(Integer id, Player player) {
        Player playerToUpdate = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id "+ id + " not found."));
        
		playerToUpdate.setName(player.getName());
        playerToUpdate.setNationality(player.getNationality());
		playerToUpdate.setBirthDate(player.getBirthDate());
        playerToUpdate.setTitles(player.getTitles());
        
		return playerRepository.save(playerToUpdate);
    }

	public Player patch(Integer id, Map<String, Object> playerProps) {
		Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id "+ id + " not found."));

		playerProps.forEach((key, value) -> {
			System.out.println("Key: " + key + " Value: " + value);
			Field field = ReflectionUtils.findField(Player.class, key);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, player, value);
		});

		return playerRepository.save(player);
	}

	@Transactional
	public void patchTitles(int id, int titles) {
		Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id "+ id + " not found."));
		playerRepository.updateTitles(player.getId(), titles);
	}

	public String delete(int id) {
		Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id "+ id + " not found."));
		playerRepository.delete(player);
		return "Deleted player with id: " + id;
	}
	
}
