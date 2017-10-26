package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动补全方法注解<br>
 * 代码示例
 * <p>
 * 	@TabComplete(value = "example")
 * 	public List<String> tabComplete(String[] args){
 * 		return Arrays.asList("TabComplete");
 * 	}
 * </p>
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface TabComplete {

	/**
	 * 指令的名称
	 */
	String[] value();
}
