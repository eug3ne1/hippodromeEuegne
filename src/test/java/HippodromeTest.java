import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    Hippodrome hippodrome;

    @Test
    void nullConstructorParamException() {
        assertThrows(IllegalArgumentException.class,()->new Hippodrome(null));
    }

    @Test
    void nullConstructorParamExceptionName() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.",illegalArgumentException.getMessage());
    }

    @Test
    void emptyConstructorParamException() {
        assertThrows(IllegalArgumentException.class,()->new Hippodrome(new ArrayList<>()));
    }

    @Test
    void emptyConstructorParamExceptionName() {
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>())
        );
        assertEquals("Horses cannot be empty.",illegalArgumentException.getMessage());
    }

    @Test
    void getHorses() {
        ArrayList<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30 ; i++) {
            String name = "Horse "+i;
            double speed = (double) i /10;
            horses.add(new Horse(name,speed));
        }
        hippodrome = new Hippodrome(horses);
        assertEquals(horses,hippodrome.getHorses());
    }

    @Test
    void move() {
        // Arrange
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockHorse = Mockito.mock(Horse.class);
            mockHorses.add(mockHorse);
        }
        Hippodrome hippodrome = new Hippodrome(mockHorses);

        hippodrome.move();

        mockHorses.forEach(horse -> verify(horse, times(1)).move());
    }

    @Test
    void getWinner(){
        Horse horse1 = new Horse("Horse1",1.0,20);
        Horse horse2 = new Horse("Horse1",1.0,10);

        List<Horse> horses = new ArrayList<>();
        horses.add(horse1);
        horses.add(horse2);
        hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();
        assertEquals(horse1,winner);
    }


}