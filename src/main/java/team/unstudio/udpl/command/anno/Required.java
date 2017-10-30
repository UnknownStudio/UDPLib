package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Required {
	/**
	 * 参数名
	 */
	String name() default "";
	
	/**
	 * 自动补全
	 */
	String[] complete() default {};
}
