import java.time.LocalDate;


public class Cashier {

    public String checkout(Cart cartWithCatalogWithProducts, String date) {

        if (cartWithCatalogWithProducts.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        String[] my = date.split("/");
        LocalDate expirationDate = LocalDate.of(Integer.parseInt(my[1]),Integer.parseInt(my[0]),LocalDate.now().getDayOfMonth());

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Card is expired");
        }
        return "transactionId";
    }
}
