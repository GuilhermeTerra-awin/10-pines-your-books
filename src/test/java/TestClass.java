import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    @Test
    void test1() {
        var cart = new Cart("1");
        assertTrue(cart.isEmpty());
    }

    @Test
    void test2() {
        var cart = new Cart("1");
        cart.add("apple");
        assertFalse(cart.isEmpty());
        assertTrue(cart.contains("apple"));
    }

    @Test
    void test3() {
        var cartManager = new CartManager();
        assertFalse(cartManager.createCart().getId().isEmpty());
    }

    @Test
    void test4() {
        var cartManager = new CartManager();

        String id1 = cartManager.createCart().getId();
        String id2 = cartManager.createCart().getId();

        assertNotEquals(id1, id2);
    }

    private class Cart {
        private final List<String> items = new ArrayList<>();
        private final String id;

        public Cart(String id) {
            this.id = id;
        }

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
            return id;
        }
    }

    private class CartManager {

        public Cart createCart() {
            return new Cart(UUID.randomUUID().toString());
        }
    }
}
