package com.seitenbau.testing.dbunit.dsl

import java.util.List;

import com.seitenbau.testing.dbunit.dsl.TableParser;

import groovy.lang.Closure;

public class TableParser {
	private static class Context {
		IParsedTableRowCallback callback
		TableRowModel activeRow
	}

	private static ThreadLocal<Context> context = new ThreadLocal<Context>()

	public static or(self, arg) {
		appendRow(self, arg)
	}

	public static or(Integer self, Integer arg) {
		appendRow(self, arg)
	}

	public static or(Boolean self, Boolean arg) {
		appendRow(self, arg)
	}

	/**
	 * Called when a new row starts
	 */
	public static appendRow(value, nextValue) {
		Context currentContext = context.get()
		TableRowModel lastRow = currentContext.activeRow
		if (lastRow != null) {
			currentContext.callback.parsedRow(lastRow);
		}
		TableRowModel row = new TableRowModel(values: [value])
		currentContext.activeRow = row
		row.or(nextValue)
	}

	public static void parseTable(Closure rows, Object owner, IParsedTableRowCallback callback) {
		Context currentContext = new Context();
		currentContext.callback = callback;
		context.set(currentContext);
		use(TableParser) {
			rows.delegate = owner
			//rows.resolveStrategy = Closure.DELEGATE_FIRST
			rows()
		}

		if (currentContext.activeRow != null) {
			callback.parsedRow(currentContext.activeRow);
		}
		context.remove();
	}
}