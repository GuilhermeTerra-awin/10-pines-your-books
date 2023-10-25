import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void cartsAreCreatedEmpty (){
        assertTrue(createCartWithCatalogWithProducts().isEmpty());
    }

    @Test
    public void cartIsNotEmptyAfterAddingProducts (){
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket());

        assertFalse(cart.isEmpty());
    }

    @Test
    public void cartContainsAddedProduct(){
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket());

        assertTrue(cart.contains(productSellBySupermarket()));
    }

    @Test
    public void cartCanContainManyProducts(){
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket());
        cart.add(otherProductSellBySupermarket());

        assertTrue(cart.contains(productSellBySupermarket()));
        assertTrue(cart.contains(otherProductSellBySupermarket()));
    }

    @Test
    public void testCanNotAddProductsNotSellByTheSupermarket() {
        Cart cart = createCartWithCatalogWithProducts();

        try {
            cart.add(productNotSellBySupermarket());
            fail(); //assertTrue(false)
        } catch (RuntimeException e) {
            assertEquals(Cart.PRODUCT_IS_NOT_SELL_BY_SUPERMARKET,e.getMessage());
            assertTrue(cart.isEmpty());
        }
    }

    @Test
    public void testCanNotAddLessThanOneProduct() {
        Cart cart = createCartWithCatalogWithProducts();

        try {
            cart.add(productSellBySupermarket(),0);
            fail();
        } catch (RuntimeException e) {
            assertEquals(Cart.PRODUCT_QUANTITY_MUST_BE_STRICTLY_POSITIVE,e.getMessage());
            assertTrue(cart.isEmpty());
        }
    }

    @Test
    public void testCanAddManyProductsAtTheSameTime() {
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket(),2);

        assertEquals(2,cart.numberOf(productSellBySupermarket()));
    }

    @Test
    public void testCanAddSameProductMoreThanOnce() {
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket(),2);
        cart.add(productSellBySupermarket(),3);

        assertEquals(5,cart.numberOf(productSellBySupermarket()));
    }

    @Test
    public void testCanAddManyDifferentProductsAtTheSameTime() {
        Cart cart = createCartWithCatalogWithProducts();

        cart.add(productSellBySupermarket(),2);
        cart.add(otherProductSellBySupermarket(),3);

        assertEquals(2,cart.numberOf(productSellBySupermarket()));
        assertEquals(3,cart.numberOf(otherProductSellBySupermarket()));
    }

    @Test
    public void testCartShouldNotCheckoutWhenEmpty(){
        Cashier cashier = new Cashier();
        var date = "10/9999";
        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(createCartWithCatalogWithProducts(), date));
        assertEquals("Cart is empty", e.getMessage());
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasOneProduct(){
        Cashier cashier = new Cashier();
        var cart = createCartWithCatalogWithProducts();
        cart.add(productSellBySupermarket());
        var date = "10/9999";

        assertEquals("transactionId", cashier.checkout(cart, date));
    }

    @Test
    public void testCartShouldCheckoutWhenCartHasMultipleProduct(){
        Cashier cashier = new Cashier();
        var cart = createCartWithCatalogWithProducts();
        var date = "10/9999";
        cart.add(productSellBySupermarket());
        cart.add(otherProductSellBySupermarket());

        assertEquals("transactionId", cashier.checkout(cart, date));
    }

    @Test
    public void test5() {
        Cashier cashier = new Cashier();
        var cart = createCartWithCatalogWithProducts();
        // I want current month and year of today
        var date = "09/2023";
        cart.add(productSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, date));
        assertEquals("Card is expired", e.getMessage());

    }

    @Test
    public void test6() {
        Cashier cashier = new Cashier();
        var cart = createCartWithCatalogWithProducts();
        // I want current month and year of today
        var date = "09/2023";

        cart.add(productSellBySupermarket());

        var e = assertThrows(RuntimeException.class , () -> cashier.checkout(cart, date));
        assertEquals("Card is expired", e.getMessage());

    }



    public Cart createCartWithCatalogWithProducts() {
        return new Cart(catalogWithProducts());
    }

    private List<Object> catalogWithProducts() {
        List<Object> catalog = new ArrayList<Object>();

        catalog.add(productSellBySupermarket());
        catalog.add(otherProductSellBySupermarket());

        return catalog;
    }

    private Object productNotSellBySupermarket() {
        return "productNotSellBySupermarket";
    }

    private Object otherProductSellBySupermarket() {
        return "otherProductSellBySupermarket";
    }

    private Object productSellBySupermarket() {
        return "productSellBySupermarket";
    }

    private Card validCard(){
        returns new Card("1234567890123456","name", MonthDay.of(9, 2023));
    }
}
