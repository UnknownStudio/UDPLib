package team.unstudio.udpl;

import org.junit.Test;
import static org.junit.Assert.*;
import team.unstudio.udpl.i18n.ClassLoaderI18n;
import team.unstudio.udpl.i18n.SLangI18n;

import java.util.Locale;

public class I18nTest {
    @Test
    public void testClassLoaderI18n(){
        ClassLoaderI18n i18n = new ClassLoaderI18n(I18nTest.class.getClassLoader(), "lang/simple/");
        i18n.setDefaultLocale(Locale.US);

        assertEquals("Internationalization %s.", i18n.localize("test"));
        assertEquals("Internationalization Translate.", i18n.format(i18n.getDefaultLocale(), "test", "Translate"));
        assertEquals("Internationalization Tester.", i18n.format(i18n.getDefaultLocale(), "test", "test2"));

        i18n.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);

        assertEquals("国际化 %s.", i18n.localize("test"));
        assertEquals("国际化 翻译.", i18n.format(i18n.getDefaultLocale(), "test", "翻译"));
        assertEquals("国际化 测试.", i18n.format(i18n.getDefaultLocale(), "test", "test2"));
    }

    @Test
    public void testSLang(){
        SLangI18n sLang = SLangI18n.fromClassLoader(I18nTest.class.getClassLoader(), "lang/slang/general.slang");
        assertNotNull(sLang);
        assertEquals("测试", sLang.localize(Locale.SIMPLIFIED_CHINESE, "test"));
        assertEquals("Test", sLang.localize(Locale.US, "test"));
        assertEquals("Test ", sLang.localize(Locale.UK, "test"));
    }
}
