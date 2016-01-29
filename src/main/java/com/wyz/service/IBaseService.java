package com.wyz.service;

import java.util.List;

public interface IBaseService<T> {
	int add(T t);

	int delete(long id);

	int update(T t);

	T find(long id);

	long getCount();

	long getCount(int key, long value);

	List<T> initTop(int limit);

	List<T> paginate(int start, int limit);

	List<T> paginate(int key, long value, int start, int limit);
}
