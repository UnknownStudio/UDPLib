package team.unstudio.udpl.command.anno;

import org.bukkit.command.CommandSender;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记一个指令执行方法<br>
 * 该方法的返回值应为void<br>
 * 该方法第一个参数应为CommandSender或其子类，其他参数应先@Required参数再@Optional参数<br>
 * 最后一个参数可以为String[]来接收额外输入的参数<br>
 * 
 * 示例如下:<br>
 * <pre>
 * @Command(value = "example",senders = Player.class,permission = "example.send")
 * public void example(CommandSender sender,@Required Player target,@Optional("default") String message){
 * 	target.sendMessage(message);
 * }
 * </pre>
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Command {
	
	/**
	 * 指令名
	 */
	String[] value() default {};
	
	/**
	 * 指令权限
	 */
	String permission() default "";
	
	/**
	 * 允许的指令发送者
	 */
	Class<? extends CommandSender>[] senders() default {CommandSender.class};
	
	/**
	 * 指令用法
	 */
	String usage() default "";
	
	/**
	 * 指令描述
	 */
	String description() default "";
	
	/**
	 * 允许OP无视权限执行指令
	 */
	boolean allowOp() default true;
	
	/**
	 * 精确参数匹配
	 */
	boolean exactParameterMatching() default false;
	
	/**
	 * 隐藏的指令，如果为true，该指令的部分信息将不会显示（例如帮助信息）
	 */
	boolean hide() default false;
}
