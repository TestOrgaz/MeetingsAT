package entities.tariff;

import lombok.Getter;
import lombok.Setter;

public class TariffOption {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer basePrice;
}
