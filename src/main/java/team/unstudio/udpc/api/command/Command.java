package team.unstudio.udpc.api.command;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.bukkit.command.CommandSender;

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
