package team.unstudio.udpl.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 注解一个配置项
 * value为在配置文件中的key
 * 可以为注解的配置项设置一个默认值
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ConfigItem{
	String value();
}
