package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface TabComplete {

	/**
	 * 指令的名称
	 */
	String[] value();
}
