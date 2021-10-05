package io.github.riesenpilz.nmsUtilities.reflections;

import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;

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
		return (T) new Field(instaceAndClass.getClass(), fieldName).get(instaceAndClass);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getFromSuper(Object instaceAndClass, String fieldName, Class<T> type) {
		return (T) new Field(instaceAndClass.getClass().getSuperclass(), fieldName).get(instaceAndClass);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getConstant(Class<?> clazz, String fieldName, Class<T> type) {
		return (T) new Field(clazz, fieldName).get(null);
	}

	public static void set(Object instaceAndClass, String fieldName, Object value) {
		new Field(instaceAndClass.getClass(), fieldName).set(instaceAndClass, value);
	}
	public static void setInSuper(Object instaceAndClass, String fieldName, Object value) {
		new Field(instaceAndClass.getClass().getSuperclass(), fieldName).set(instaceAndClass, value);
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
