package team.unstudio.udpc.api.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.bukkit.command.CommandSender;

/**
 * 标记一个指令执行方法
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Command {
	String[] value();
	String permission() default "";
	Class<? extends CommandSender>[] senders() default {CommandSender.class};
	Class<?>[] parameterTypes() default {};
	String usage() default "";
	String description() default "";
}
