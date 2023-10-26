package own;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    @Test
    void test3() {
        var cartManager = new CartManager();
        Cart cart = cartManager.createCart();
        assertTrue(cart.isEmpty());
        assertFalse(cart.getId().isEmpty());
    }

    @Test
    void test4() {
        var cartManager = new CartManager();
        String id1 = cartManager.createCart().getId();
        String id2 = cartManager.createCart().getId();
        assertNotEquals(id1, id2);
    }

    @Test
    void test2() {
        var cart = new Cart("1");
        cart.add("apple");
        assertFalse(cart.isEmpty());
        assertTrue(cart.contains("apple"));
    }

    @Test
    void test5() {
        var cartManager = new CartManager();
        String id = cartManager.createCart().getId();
        assertTrue(cartManager.listItems(id).isEmpty());
    }

    @Test
    void test6() {
        var cartManager = new CartManager();
        String id = cartManager.createCart().getId();
        cartManager.addToCart(id, "apple");
        cartManager.addToCart(id, "orange");
        assertEquals(2, cartManager.listItems(id).size());
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

        public List<String> getItems() {
            return Collections.unmodifiableList(this.items);
        }
    }

    private class CartManager {

        private final List<Cart> carts = new ArrayList<>();

        public Cart createCart() {
            var cart = new Cart(UUID.randomUUID().toString());
            carts.add(cart);
            return cart;
        }

        public List<String> listItems(String id) {
            return this.carts.stream().filter(cart -> cart.getId().equals(id)).findFirst().get().getItems();
        }

        public void addToCart(String id, String item) {
            this.carts.stream().filter(cart -> cart.getId().equals(id)).findFirst().get().add(item);
        }
    }
}
