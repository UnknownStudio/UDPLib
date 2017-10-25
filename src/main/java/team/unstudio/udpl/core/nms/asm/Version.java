package team.unstudio.udpl.core.nms.asm;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import team.unstudio.udpl.util.BukkitVersion;

public @interface Version {

	@Retention(RUNTIME)
	@Target({ TYPE, FIELD, METHOD })
	public static @interface Only{
		BukkitVersion[] value();
	}
	
	@Retention(RUNTIME)
	@Target({ TYPE, FIELD, METHOD })
	public static @interface Above{
		BukkitVersion value();
	}
	
	@Retention(RUNTIME)
	@Target({ TYPE, FIELD, METHOD })
	public static @interface Below{
		BukkitVersion value();
	}
	
	@Retention(RUNTIME)
	@Target({ TYPE, FIELD, METHOD })
	public static @interface Source{
		BukkitVersion bukkit();
		String minecraft();
	}
}
