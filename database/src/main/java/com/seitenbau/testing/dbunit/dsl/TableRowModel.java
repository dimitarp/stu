package com.seitenbau.testing.dbunit.dsl;

import java.util.ArrayList;
import java.util.List;


public class TableRowModel {
	private List<Object> values = new ArrayList<Object>();

	public TableRowModel or(Object arg) {
		System.out.println("TableRowModel or(" + arg + ")");
		values.add(arg);
		return this;
	}
	
	public List<Object> getValues() {
		return values;
	}

	public int getColumnCount() {
		return values.size();
	}

	public boolean isHeadRow() {
		if (values.size() == 0) {
			return false;
		}

		for (Object o : values) {
			if (!(o instanceof ColumnBinding)) {
				return false;
			}
		}

		return true;
	}

	public int getRefColumn() {
		int index = 0;
		for (Object o : values) {
			ColumnBinding<?, ?> column = (ColumnBinding<?, ?>) o;
			if (column.isRefColumn())
				return index;
			index++;
		}
		return -1;
	}

	public int getIdColumn() {
		int index = 0;
		for (Object o : values) {
			ColumnBinding<?, ?> column = (ColumnBinding<?, ?>) o;
			if (column.isIdColumn())
				return index;
			index++;
		}
		return -1;
	}

	public Object getValue(int index) {
		return values.get(index);
	}

}
