package team.unstudio.udpl.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface FastReflections {
    static List<Method> getAllMethodAnnotatedBy(Class<?> container, Class<? extends Annotation> annotation) {
        return Arrays.stream(container.getDeclaredMethods()).filter(method -> Arrays.stream(method.getDeclaredAnnotations()).anyMatch(a -> a.getClass().equals(annotation))).collect(Collectors.toList());
    }

    static List<Field> getAllFieldAnnotatedBy(Class<?> container, Class<? extends Annotation> annotation) {
        return Arrays.stream(container.getDeclaredFields()).filter(field -> Arrays.stream(field.getDeclaredAnnotations()).anyMatch(a -> a.getClass().equals(annotation))).collect(Collectors.toList());
    }

    static List<Parameter> getAllParameterAnnotatedBy(Executable executable, Class<? extends Annotation> annotation) {
        return Arrays.stream(executable.getParameters()).filter(parameter -> Arrays.stream(parameter.getAnnotations()).anyMatch(a -> a.getClass().equals(annotation))).collect(Collectors.toList());
    }
}
