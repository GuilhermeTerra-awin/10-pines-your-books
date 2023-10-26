package own;

public interface IMerchantProcessor {
    String processPayment(Double amount, Card card);
}
