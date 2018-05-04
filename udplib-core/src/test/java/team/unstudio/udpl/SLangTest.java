package team.unstudio.udpl;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import team.unstudio.udpl.i18n.slang.CachedSLang;
import team.unstudio.udpl.i18n.slang.SLangSpliter;

import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class SLangTest {
    private CachedSLang[] sLang;
    private String[] datas;
    private String separator;
    private List<String> checker;

    public SLangTest(Collection<String> datas, String separator, List<String> checker) {
        this.checker = checker;
        this.separator = separator;
        this.datas = datas.toArray(new String[0]);

        sLang = SLangSpliter.split(separator, this.datas);
    }

    @Parameterized.Parameters
    public static Collection input(){
        return asList(new Object[][]{
                {asList("key|en", "test| why ", "trim|trim    ∞"), "\\|", asList("test", "why", "hello", "hello", "trim", "trim    ")},
                {asList("en|zh-CN|en-US", "test|测试|TEST", "second|第二|QWQ"), "\\|", asList("test", "测试", "test", "TEST", "second", "第二", "second", "QWQ", "why", "why")},
                {asList("zh-CN en", "卧槽 woc"), " ", asList("卧槽", "woc")},
        });
    }

    @Test
    public void check(){
        checkF:for (int i = 0; (i + 1) < checker.size(); i += 2) {
            String index = checker.get(i);
            String expected = checker.get(i + 1);

            for (CachedSLang lang : sLang) {
                String fact = lang.get(index);
                if (fact == null) continue;
                if (expected.equals(fact)) continue checkF;

            }

            fail(String.format("Can't match %s by key %s", expected, index));
        }
    }
}
