package org.openmore.cms.entity.enums;

public interface BaseEnum<E extends Enum<?>, T> {
	public T getValue();
	public String getDisplayName();
}
