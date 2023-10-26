package hernan;

import java.time.LocalDate;
import java.util.List;

public class Cashier {

    public static final String CAN_NOT_CHECKOUT_EMPTY_CART = "Can not checkout an empty cart";
    public static final String CREDIT_CARD_EXPIRED = "Credit card is expired";

    private final List<Object> salesBook;
    private final MerchantProcessor merchantProcessor;

    public Cashier(List<Object> salesBook, MerchantProcessor merchantProcessor) {
        this.salesBook = salesBook;
        this.merchantProcessor = merchantProcessor;
    }

    public double checkOut(Cart cart, CreditCard creditCard, LocalDate today) {
        assertCartIsNotEmpty(cart);
        assertCreditCardIsNotExpired(creditCard, today);

        double total = cart.total();
        merchantProcessor.debit(creditCard, total);
        salesBook.add(total);

        return total;
    }

    public void assertCreditCardIsNotExpired(CreditCard creditCard, LocalDate today) {
        if (creditCard.isExpiredOn(today)) throw new RuntimeException(CREDIT_CARD_EXPIRED);
    }

    public void assertCartIsNotEmpty(Cart cart) {
        if (cart.isEmpty()) throw new RuntimeException(CAN_NOT_CHECKOUT_EMPTY_CART);
    }

    public boolean salesBookIsEmpty() {
        return salesBook.isEmpty();
    }

}
