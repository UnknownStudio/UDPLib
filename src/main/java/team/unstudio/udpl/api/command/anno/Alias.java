package team.unstudio.udpl.api.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 指令的别名
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Alias {
	String[] value();
}
