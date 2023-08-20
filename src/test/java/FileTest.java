

import br.com.dlqs.prenotamifx.player.Mp3Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class FileTest {
    @Test
    void test(){
        Mp3Player mp3Player = new Mp3Player();
        mp3Player.play("victory.mp3");
    }

}
