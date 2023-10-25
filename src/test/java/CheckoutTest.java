import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutTest {

    private Cashier cashier;

    @BeforeEach
    public void Setup(){
        cashier = new Cashier(new MerchantProcessorMock());
    }

    @Test
    public void testCartShouldNotCheckoutWhenEmpty(){
        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(TestDataFactory.createCartWithCatalogWithProducts(), TestDataFactory.createValidCard() ));
        assertEquals("Cart is empty", e.getMessage());
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasOneProduct(){
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        assertEquals("transactionId", cashier.checkout(cart, TestDataFactory.createValidCard() ));
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasMultipleProduct(){
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());
        cart.add(TestDataFactory.otherProductSellBySupermarket());

        assertEquals("transactionId", cashier.checkout(cart, TestDataFactory.createValidCard() ));
    }

    @Test
    public void test5() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, TestDataFactory.createExpiredCard() ));
        assertEquals("Card is expired", e.getMessage());

    }

    @Test
    public void test6() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, TestDataFactory.createExpiredCard() ));
        assertEquals("Card is expired", e.getMessage());

    }

    @Test
    public void test7() {
        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.productSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, TestDataFactory.createStolenCard()));
        assertEquals("Card is stolen", e.getMessage());
    }

    @Test
    public void test8() {

        var cart = TestDataFactory.createCartWithCatalogWithProducts();
        cart.add(TestDataFactory.otherProductSellBySupermarket());
        cart.add(TestDataFactory.otherProductSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, TestDataFactory.createValidCard()));
        assertEquals("Card balance exceeded", e.getMessage());
    }
}
