package own;

import java.time.YearMonth;


public class Card {

    public final String name;
    public final YearMonth expiresAt;
    public final String number;

    public Card(String number, String name, YearMonth expiresAt) {
        this.name = name;
        this.expiresAt = expiresAt;
        this.number = number;
    }

    public boolean isExpired(YearMonth currentYearMonth) {
        return expiresAt.isBefore(currentYearMonth);
    }


}
