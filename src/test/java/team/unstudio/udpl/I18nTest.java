package team.unstudio.udpl;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import team.unstudio.udpl.i18n.ClassLoaderI18n;
import team.unstudio.udpl.i18n.I18n;

import java.util.Locale;

public class I18nTest {
    private static ClassLoaderI18n i18n;

    @BeforeClass
    public static void init(){
        i18n = new ClassLoaderI18n(I18n.class.getClassLoader(), "lang/");
        i18n.setDefaultLocale(Locale.US);
        i18n.getRawCache().set("test", "Internationalization %s.");
        i18n.getRawCache().set("test2", "Tester");
    }

    @Test
    public void testClassLoaderI18n(){
        assertEquals("Internationalization %s.", i18n.localize("test"));
        assertEquals("Internationalization Translate.", i18n.format(i18n.getDefaultLocale(), "test", "Translate"));
        assertEquals("Internationalization Tester.", i18n.format(i18n.getDefaultLocale(), "test", "test2"));
    }
}
