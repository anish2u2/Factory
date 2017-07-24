package org.factory.design.collection;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionFactory {

	@SuppressWarnings("rawtypes")
	public static Map createConcurrentMap(int initialCapacity) {
		return new ConcurrentHashMap(initialCapacity);
	}

	@SuppressWarnings("rawtypes")
	public static Map createLinkedHashMap(int initialCapacity) {
		return new LinkedHashMap(initialCapacity);
	}

	@SuppressWarnings("rawtypes")
	public static List createLinkedList() {
		return new LinkedList();
	}

	@SuppressWarnings("rawtypes")
	public static List createArrayList(int initialCapacity) {
		return new ArrayList(initialCapacity);
	}

	@SuppressWarnings("rawtypes")
	public static Map createIdentityHashMap(int expectedMaxSize) {
		return new IdentityHashMap(expectedMaxSize);
	}

}
