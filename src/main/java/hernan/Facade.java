package hernan;

import javax.xml.catalog.Catalog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Facade {

    private final Map<String, Cart> carts = new HashMap<>();

    private final AuthenticationService authenticationService;

    private final Map<Object, Double> catalog;

    public Facade(AuthenticationService authenticationService, Map<Object, Double> catalog) {
        this.authenticationService = authenticationService;
        this.catalog = catalog;
    }

    public String createCart(String userName, String password) {

        if (!authenticationService.authenticate(userName, password)) {
            throw new RuntimeException("Invalid user");
        }

        Cart cart = new Cart(catalog);
        String cartId = UUID.randomUUID().toString();
        carts.put(cartId, cart);
        return cartId;
    }

    public List<Object> listCart(String cartId) {
        return carts.get(cartId).getProducts();
    }

    public void addProductToCart(String cartId, Object product, int quantity) {
        carts.get(cartId).add(product, quantity);
    }
}
