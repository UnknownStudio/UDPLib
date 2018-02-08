package team.unstudio.udpl.command.anno;

import java.util.List;

public interface CommandParameterManager {
	
	Object transform(Class<?> clazz, String value) throws Exception;

	List<String> tabComplete(Class<?> clazz, String value);
}
