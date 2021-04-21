package entities.tariff;

import framework.enums.localization.Localization;
import lombok.Getter;
import lombok.Setter;

public class Module extends TariffOption {
    @Getter
    @Setter
    private String localeKey;

    public String getLocalizedName() {
        return Localization.getInstance().getValue(this.localeKey);
    }
}
