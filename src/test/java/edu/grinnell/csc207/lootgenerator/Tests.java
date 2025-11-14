package edu.grinnell.csc207.lootgenerator;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.DisplayName;

import net.jqwik.api.Property;

public class Tests {
    @Property(tries=10000000)
    @DisplayName("Run program 10,000,000 times")
    public void burnTest() throws FileNotFoundException {
        assertDoesNotThrow(() -> {
                LootGenerator.main(new String[]{});
            });
    }
}