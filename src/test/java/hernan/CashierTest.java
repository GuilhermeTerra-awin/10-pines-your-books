package hernan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {

    private SupermarketTestObjects testObjects;

    @BeforeEach
    public void setUp() {
        testObjects = new SupermarketTestObjects();
    }

    @Test
    public void canNotCheckOutAnEmptyCart() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        Cashier cashier = new Cashier(testObjects.emptySalesBook(),
                testObjects.failMerchantProcessor());

        try {
            cashier.checkOut(cart, testObjects.notExpiredCreditCard(), testObjects.today());
            fail();
        } catch (RuntimeException e) {
            assertEquals(Cashier.CAN_NOT_CHECKOUT_EMPTY_CART, e.getMessage());
            assertTrue(cashier.salesBookIsEmpty());
        }
    }

    @Test
    public void cashierCalculatesSalesTotalCorrectly() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());
        cart.add(testObjects.otherProductSellBySupermarket(), 2);

        Cashier cashier = new Cashier(testObjects.emptySalesBook(), testObjects.alwaysSuccessMerchantProcessor());

        double saleTotal = cashier.checkOut(cart, testObjects.notExpiredCreditCard(), testObjects.today());
        double expectedSalesTotal =
                testObjects.productSellBySupermarketPrice() +
                        testObjects.otherProductSellBySupermarketPrice() * 2;

        assertEquals(expectedSalesTotal, saleTotal, 0.01);
    }

    @Test
    public void cashierRegistersSaleOnSuccessfulCheckOut() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());

        Cashier cashier = new Cashier(testObjects.emptySalesBook(), testObjects.alwaysSuccessMerchantProcessor());

        cashier.checkOut(cart, testObjects.notExpiredCreditCard(), testObjects.today());

        assertFalse(cashier.salesBookIsEmpty());
    }

    @Test
    public void canNotCheckOutWithExpiredCreditCard() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());
        Cashier cashier = new Cashier(testObjects.emptySalesBook(),
                testObjects.failMerchantProcessor());

        try {
            cashier.checkOut(cart, testObjects.expiredCreditCard(), testObjects.today());
            fail();
        } catch (RuntimeException e) {
            assertEquals(Cashier.CREDIT_CARD_EXPIRED, e.getMessage());
            assertTrue(cashier.salesBookIsEmpty());
            //asegurar que no uso el MP
        }
    }

    @Test
    public void canNotCheckOutWithAnStolenCreditCard() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());

        MerchantProcessor merchantProcessorForStolenCC = (creditCard, amount)
                -> {
            throw new RuntimeException(MerchantProcessor.CREDIT_CARD_STOLEN);
        };
        Cashier cashier = new Cashier(testObjects.emptySalesBook(),
                merchantProcessorForStolenCC);

        try {
            cashier.checkOut(cart, testObjects.notExpiredCreditCard(), testObjects.today());
            fail();
        } catch (RuntimeException e) {
            assertEquals(MerchantProcessor.CREDIT_CARD_STOLEN, e.getMessage());
            assertTrue(cashier.salesBookIsEmpty());
        }
    }

    @Test
    public void canNotCheckOutWhenCreditCardHasNoCredit() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());

        Cashier cashier = new Cashier(testObjects.emptySalesBook(),
                (creditCard, amount) -> {
                    throw new RuntimeException(MerchantProcessor.CREDIT_CARD_WITHOUT_CREDIT);
                });

        try {
            cashier.checkOut(cart, testObjects.notExpiredCreditCard(), testObjects.today());
            fail();
        } catch (RuntimeException e) {
            assertEquals(MerchantProcessor.CREDIT_CARD_WITHOUT_CREDIT, e.getMessage());
            assertTrue(cashier.salesBookIsEmpty());
        }
    }

    @Test
    public void merchantProcessorReceviesTheRightCreditCardAndAmount() {
        Cart cart = testObjects.createCartWithCatalogWithProducts();
        cart.add(testObjects.productSellBySupermarket());
        CreditCard notExpiredCreditCard = testObjects.notExpiredCreditCard();

        Cashier cashier = new Cashier(testObjects.emptySalesBook(),
                (creditCard, amount) -> {
                    assertEquals(notExpiredCreditCard, creditCard);
                    assertEquals(cart.total(), amount, 0.01);
                });

        cashier.checkOut(cart, notExpiredCreditCard, testObjects.today());
    }

}
