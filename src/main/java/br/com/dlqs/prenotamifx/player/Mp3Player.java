package br.com.dlqs.prenotamifx.player;


import javazoom.jl.player.Player;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.util.Objects;

@Component
public class Mp3Player {

    private Player player;

    public void play(String filename) {
        try {
            System.out.println(filename);
            BufferedInputStream buffer = new BufferedInputStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filename)));
            player = new Player(buffer);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
