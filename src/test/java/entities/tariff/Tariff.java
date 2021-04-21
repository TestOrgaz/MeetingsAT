package entities.tariff;

import framework.enums.localization.Localization;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Tariff {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer basePrice;

    @Getter
    @Setter
    private Integer Duration;

    @Getter
    @Setter
    private String localeKey;

    @Getter
    @Setter
    private List<TariffOption> optionList;

    public String getLocalizedName() {
        return Localization.getInstance().getValue(this.localeKey);
    }
}
