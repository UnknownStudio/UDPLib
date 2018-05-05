package team.unstudio.udpl.i18n;

import java.util.Locale;

public abstract class BaseI18n implements I18n {
    /**
     * default locale from {@link Locale#getDefault()}
     */
    private Locale defaultLocale = Locale.getDefault();

    private I18n parent;

    @Override
    public I18n getParent() {
        return parent;
    }

    @Override
    public void setParent(I18n parent) {
        this.parent = parent;
    }

    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = Locale.forLanguageTag(defaultLocale);
    }


}
