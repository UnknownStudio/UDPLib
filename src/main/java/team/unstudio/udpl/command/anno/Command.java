package team.unstudio.udpl.command.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.bukkit.command.CommandSender;

/**
 * 标记一个指令执行方法<br>
 * 该方法的返回值应为void或boolean。若为boolean，返回true为指令执行成功，返回false为指令执行失败<br>
 * 该方法第一个参数应为CommandSender或其子类，其他参数应先@Required参数再@Optional参数，不得有其他参数<br>
 * 
 * 示例如下:
 * <br>
	@Command(value = "give",senders = Player.class)
	public boolean giveItemToPlayer(CommandSender sender,@Required Player target,@Required Material itemType,@Optional("1") int amount,@Optional("0") short durability){
		if(target==null)
			return false;
		
		if(itemType==null)
			return false;
		
		ItemStack item = new ItemStack(itemType);
		item.setAmount(amount);
		item.setDurability(durability);
		
		target.getInventory().addItem(item);
		return true;
	}
 * </p>
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
}
