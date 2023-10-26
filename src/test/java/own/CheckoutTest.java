package own;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutTest {

    private Cashier cashier;

    @BeforeEach
    public void init() {
        // create stub for own.IMerchantProcessor
        var procesor = new IMerchantProcessor() {
            @Override
            public String processPayment(Double amount, Card card) {
                if (card.number.equals("1111111111111111")) {
                    throw new RuntimeException("own.Card is stolen");
                }
                if (card.number.equals("22222222222222222")) {
                    throw new RuntimeException("own.Card is expired");
                }
                if (card.number.equals("3333333333333333")) {
                    throw new RuntimeException("own.Card balance exceeded");
                }
                return "transactionId";

            }
        };

        // create own.Cashier instance with stub
        cashier = new Cashier(procesor);
    }

    @Test
    public void testCartShouldNotCheckoutWhenEmpty() {
        var e = assertThrows(RuntimeException.class, () -> cashier.checkout(TestDataFactory.createCartWithCatalogWithProducts(), TestDataFactory.createValidCard()));
        assertEquals("own.Cart is empty", e.getMessage());
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasOneProduct() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        assertEquals("transactionId", cashier.checkout(cart, TestDataFactory.createValidCard()));
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasMultipleProduct() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());
        cart.add(TestDataFactory.otherProductSellBySupermarket());

        assertEquals("transactionId", cashier.checkout(cart, TestDataFactory.createValidCard()));
    }

}
