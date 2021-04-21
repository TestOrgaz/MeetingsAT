package entities.tariff;

import lombok.Getter;
import lombok.Setter;

public class Extension extends TariffOption {
    @Getter
    @Setter
    private Integer amount;

    public void addNumber(Integer count) {
        Integer initialCount = this.getAmount() != null ? this.getAmount() : 0;
        this.setAmount(initialCount + count);
    }
}
