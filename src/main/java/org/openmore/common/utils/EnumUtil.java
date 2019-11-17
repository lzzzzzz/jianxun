package org.openmore.common.utils;

public class EnumUtil {
 
	public static <T extends Enum<?>, S extends Enum<?>> T convertEnum(S source, Class<T> targetClass) {
		if (source instanceof Enum) {
			String sourceEnum = ((Enum<?>) source).name();
			try {
				return getEnumObject(sourceEnum, targetClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
 
	}
 
	private static <T extends Enum<?>> T getEnumObject(String value, Class<T> clazz) {
		if (!clazz.isEnum()) {
			return null;
		}
		try {
			T[] enumConstants = clazz.getEnumConstants();
			for (T ec : enumConstants) {
				if (((Enum<?>) ec).name().equals(value)) {
					return ec;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
}