package io.github.riesenpilz.nmsUtilities.reflections;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;

/**
 * Represents a {@link java.lang.reflect.Field}
 *
 */
public class Field {

	private java.lang.reflect.Field field;

	public Field(Class<?> clazz, String fieldName) {
		Validate.notNull(clazz);
		Validate.notNull(fieldName);
		try {
			Validate.notNull(field = clazz.getDeclaredField(fieldName));
			field.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object instaceAndClass, String fieldName, Class<T> type) {
		Validate.notNull(instaceAndClass);
		Validate.notNull(type);
		return (T) new Field(instaceAndClass.getClass(), fieldName).get(instaceAndClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFromSuper(Object instaceAndChildClass, String fieldName, Class<T> type) {
		Validate.notNull(instaceAndChildClass);
		Validate.notNull(type);
		return (T) new Field(instaceAndChildClass.getClass().getSuperclass(), fieldName).get(instaceAndChildClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getConstant(Class<?> clazz, String fieldName, Class<T> type) {
		Validate.notNull(type);
		return (T) new Field(clazz, fieldName).get(null);
	}

	public static void set(Object instaceAndClass, String fieldName, Object value) {
		new Field(instaceAndClass.getClass(), fieldName).set(instaceAndClass, value);
	}

	public static void setInSuper(Object instaceAndChildClass, String fieldName, Object value) {
		Validate.notNull(instaceAndChildClass);
		new Field(instaceAndChildClass.getClass().getSuperclass(), fieldName).set(instaceAndChildClass, value);
	}

	public void set(Object instance, Object value) {
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public java.lang.reflect.Field getField() {
		return field;
	}

	public Object get(@Nullable Object instance) {
		try {
			return field.get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
