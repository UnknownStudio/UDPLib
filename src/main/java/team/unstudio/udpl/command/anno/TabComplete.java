package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动补全方法注解<br>
 * 必须将该方法置于与需要补全的@Command相同的类下<br>
 * <code>
 * 	@TabComplete(value = "permission") <br>
 * 	public List<String> tabComplete(String[] args){ <br>
 * 		return Arrays.asList("TabComplete"); <br>
 * 	} <br>
 * </code>
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface TabComplete {

	/**
	 * 指令的名称
	 */
	String[] value();
}
