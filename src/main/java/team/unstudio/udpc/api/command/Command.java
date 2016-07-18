package team.unstudio.udpc.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bukkit.command.CommandSender;

/**
 * 表示一个指令处理器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	
	/**
	 * 指令
	 * @return
	 */
	String value();
	/**
	 * 权限
	 * @return
	 */
	String permission();
	/**
	 * 指定发送者
	 * @return
	 */
	Class<? extends CommandSender>[] sender() default CommandSender.class;
	/**
	 * 参数数量
	 * @return
	 */
	int parameter();
	
}
