package jeelab.model.builder;

public interface EntityBuilder<E> {

	public E build(E entity);
	public E build();
	
}
