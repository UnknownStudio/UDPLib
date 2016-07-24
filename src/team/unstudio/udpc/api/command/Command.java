package team.unstudio.udpc.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.command.CommandSender;

/**
 * 表示一个指令
 * 被注解的方法必须参数为CommandSender,Object[],返回值为boolean或void;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	
	/**
	 * 指令
	 * @return
	 */
	String[] value();
	
	/**
	 * 权限
	 * @return
	 */
	String permission() default "";
	
	/**
	 * 发送者
	 * @return
	 */
	Class<? extends CommandSender>[] sender() default CommandSender.class;
	
	/**
	 * 参数
	 * @return
	 */
	Class<?>[] parameter() default void.class;
}
