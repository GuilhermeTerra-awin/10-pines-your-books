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
        assertTrue(cart.contains("apple"));
    }

    @Test
    void test3(){
        var cartManager = new CartManager();
        assertEquals("id",cartManager.createCart().getId());
    }

    private class Cart {
        private final List<String> items = new ArrayList<>();

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public void add(String item) {
            items.add(item);
        }

        public boolean contains(String item) {
            return items.contains(item);
        }

        public String getId() {
            return "id";
        }
    }

    private class CartManager {


        public Cart createCart() {
            return new Cart();
        }
    }
}
