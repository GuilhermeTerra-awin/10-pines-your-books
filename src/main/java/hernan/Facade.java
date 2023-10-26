package hernan;

import javax.xml.catalog.Catalog;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class Facade {

    private final Map<String, Cart> carts = new HashMap<>();

    private final AuthenticationService authenticationService;

    private final Map<Object, Double> catalog;

    private final List<Object> salesBook;

    private final MerchantProcessor merchantProcessor;

    public Facade(AuthenticationService authenticationService, Map<Object, Double> catalog, MerchantProcessor merchantProcessor) {
        this.authenticationService = authenticationService;
        this.catalog = catalog;
        this.salesBook = new ArrayList<>();
        this.merchantProcessor = merchantProcessor;

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

        if (!carts.containsKey(cartId)) {
            throw new RuntimeException("Cart does not exist");
        }
        return carts.get(cartId).getProducts();
    }

    public void addProductToCart(String cartId, Object product, int quantity) {
        carts.get(cartId).add(product, quantity);
    }

    public String checkout(String cartID, String creditCardNumber, String creditCardExpiration, String name) {
        new Cashier(salesBook, merchantProcessor)
                .checkOut(carts.get(cartID), new CreditCard( YearMonth.parse(creditCardExpiration)), LocalDate.now());
        return "transactionId";
    }
}
