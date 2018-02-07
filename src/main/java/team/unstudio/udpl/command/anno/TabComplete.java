package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.bukkit.command.CommandSender;

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
	 * 补全的名称
	 */
	String[] value();
	
	/**
	 * 补全权限
	 */
	String permission() default "";
	
	/**
	 * 允许的发送者
	 */
	Class<? extends CommandSender>[] senders() default {CommandSender.class};
	
	/**
	 * 允许OP无视权限执行补全
	 */
	boolean allowOp() default true;
}
