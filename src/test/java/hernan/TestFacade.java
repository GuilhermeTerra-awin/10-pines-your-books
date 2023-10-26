package hernan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFacade {

    private Facade facade;
    private SupermarketTestObjects supermarketTestObjects;

    @BeforeEach
    void setUp() {
        supermarketTestObjects = new SupermarketTestObjects();
        AuthenticationService authenticationService = (userName, password) -> validUserName().equals(userName) && validPassword().equals(password);
        facade = new Facade(authenticationService, supermarketTestObjects.catalogWithProducts(), supermarketTestObjects.merchantProcessor()
    }

    @Test
    public void shouldCreateCartWhenUserIsValid() {
        assertFalse(facade.createCart(validUserName(), validPassword()).isEmpty());
    }

    @Test
    public void shouldNotCreateValidIfUserInfoInInvalid() {
        var ex = assertThrows(RuntimeException.class, () -> facade.createCart("invalid", "invalid"));
        assertEquals("Invalid user", ex.getMessage());
    }

    @Test
    public void shouldReturnNoItemsFromNewCart() {
        String cartId = facade.createCart(validUserName(), validPassword());
        assertTrue(facade.listCart(cartId).isEmpty());
    }

    @Test
    public void shouldReturnDifferentIdsFromDifferentCarts() {
        String cartId1 = facade.createCart(validUserName(), validPassword());
        String cartId2 = facade.createCart(validUserName(), validPassword());
        assertNotEquals(cartId1, cartId2);
        assertTrue(facade.listCart(cartId1).isEmpty());
        assertTrue(facade.listCart(cartId2).isEmpty());
    }

    @Test
    public void TestListCartShouldReturnExceptionWhenCartNotExist() {
        String cartId = "Id does not exist";
        var ex = assertThrows(RuntimeException.class, () -> facade.listCart(cartId));
        assertEquals("Cart does not exist", ex.getMessage());
    }

    @Test
    public void shouldAddProductToCart() {
        String cartId = facade.createCart(validUserName(), validPassword());
        facade.addProductToCart(cartId, supermarketTestObjects.productSellBySupermarket(), 1);
        List<Object> products = facade.listCart(cartId);
        assertTrue(products.contains(supermarketTestObjects.productSellBySupermarket()));
    }

    @Test
    public void shouldNotAddNotSoldProductToCart() {
        String cartId = facade.createCart(validUserName(), validPassword());
        var ex = assertThrows(RuntimeException.class, () -> facade.addProductToCart(cartId, supermarketTestObjects.productNotSellBySupermarket(), 1));
        assertEquals(Cart.PRODUCT_IS_NOT_SELL_BY_SUPERMARKET, ex.getMessage());
        assertTrue(facade.listCart(cartId).isEmpty());
    }

    @Test
    public void shouldAddMultipleProductsToCart() {
        String cartId = facade.createCart(validUserName(), validPassword());
        facade.addProductToCart(cartId, supermarketTestObjects.productSellBySupermarket(), 2);
        facade.addProductToCart(cartId, supermarketTestObjects.otherProductSellBySupermarket(), 2);
        List<Object> products = facade.listCart(cartId);
        assertTrue(products.contains(supermarketTestObjects.productSellBySupermarket()));
        assertTrue(products.contains(supermarketTestObjects.otherProductSellBySupermarket()));
        assertEquals(4, products.size());
    }

    @Test
    public void testCheckout(){
        assertEquals("transactionId", facade.checkout("cartID","creditCardNumber","CreditCardExpiration", "name"));
    }

    private String validUserName() {
        return "user_1";
    }

    private String validPassword() {
        return "1";
    }


}
