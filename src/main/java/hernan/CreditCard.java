package hernan;

import java.time.LocalDate;
import java.time.YearMonth;

public class CreditCard {

    private final YearMonth expirationDate;

    public CreditCard(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpiredOn(LocalDate aDate) {
        return expirationDate.isBefore(YearMonth.from(aDate));
    }

}
