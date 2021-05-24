package io.github.riesenpilz.nms.reflections;

public class Field {

	private java.lang.reflect.Field field;

	public Field(Class<?> clazz, String fieldName) {
		try {
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object instaceAndClass, String fieldName, Class<T> type) {
		return (T) new Field(instaceAndClass.getClass(), fieldName).get(instaceAndClass);
	}
	public static void set(Object instaceAndClass, String fieldName, Object value) {
		new Field(instaceAndClass.getClass(), fieldName).set(instaceAndClass, value);
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

	public Object get(Object instance) {
		try {
			return field.get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
