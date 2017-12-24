package team.unstudio.udpl.command.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 可选的指令参数
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Optional {
	/**
	 * 当无参数输入时的默认值
	 */
	String value() default "";
	
	/**
	 * 参数名
	 */
	String name() default "";
	
	/**
	 * 参数用法
	 */
	String usage() default "";
	
	/**
	 * 自动补全
	 */
	String[] complete() default {};
}
