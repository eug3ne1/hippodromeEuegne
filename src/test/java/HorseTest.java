import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class HorseTest {
    
    Horse horse;


    @Test
    void nullFirstParameterException() {
        assertThrows(IllegalArgumentException.class,()->new Horse(null,2.8));
    }

    @Test
    void nullExceptionName() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.8));
        assertEquals("Name cannot be null.",illegalArgumentException.getMessage());
    }

    @Test
    void emptyFirstParameterException(){
        assertThrows(IllegalArgumentException.class,()->new Horse(" ",2.8));
    }

    @ParameterizedTest
    @ValueSource(strings = {""," ", "  "})
    void emptyExceptionName(String name) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 2.8));
        assertEquals("Name cannot be blank.",illegalArgumentException.getMessage());
    }

    @Test
    void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class,()->new Horse("Adam",-2.8));
    }

    @Test
    void negativeSpeedExceptionName() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse("Adam",-2.8));
        assertEquals("Speed cannot be negative.",illegalArgumentException.getMessage());
    }

    @Test
    void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class,()->new Horse("Adam",2.8,-100));
    }

    @Test
    void negativeDistanceExceptionName() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse("Adam", 2.8, -100));
        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void getName() {
        String name = "Pegasus";
        horse = new Horse(name,1.1);
        assertEquals(name,horse.getName());
    }


    @Test
    void getSpeed() {
        double speed = 1.1;
        horse = new Horse("Pegasus",speed);
        assertEquals(speed,horse.getSpeed());
    }

    @Test
    void getDistance() {
        double distance = 100.5;
        horse = new Horse("Pegsus",1.1,distance);
        assertEquals(distance,horse.getDistance());

    }

    @Test
    void getDistanceNotInConstructor() {
        double distance = 0.0;
        horse = new Horse("Pegsus",1.1);
        assertEquals(distance,horse.getDistance());

    }

    @Test
    void testMoveCallsGetRandomDoubleWithCorrectParameters() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            // Arrange
            Horse horse = new Horse("TestHorse", 10.0);

            // Act
            horse.move();

            // Assert
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.7,0.5,0.8,0.9})
    void testMoveCalculatesDistanceCorrectly(double randomValue) {
        double speed = 1.1;
        double distance = 20;
        double expectedDistance = distance + speed*randomValue;
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double min = 0.2;
            double max = 0.9;
            Horse horse = new Horse("TestHorse", speed, distance);
            mockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(randomValue);

            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
