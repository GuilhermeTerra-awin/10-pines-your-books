import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    @Test
    void test1() {
        var cart = new Cart();
        assertTrue(cart.isEmpty());
    }

    @Test
    void test2() {
        var cart = new Cart();
        cart.add("apple");
        assertFalse(cart.isEmpty());
    }

    @Test
    void test3() {
        var cart = new Cart();
        cart.add("apple");
        assertTrue(cart.listItems().contains("apple"));
    }

    private class Cart {
        private final List<String> items = new ArrayList<>();

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public void add(String item) {
            items.add(item);
        }

        public List<String> listItems() {
            return items;
        }
    }
}
