package com.rithsagea.skyblock;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;

public class Logger {
	private static final StringBuilder builder = new StringBuilder();
	private static final Deque<String> messages = new ArrayDeque<String>();
	
	public static void log(String text) {
		messages.add(String.format("[%s] %s\n", new Timestamp(System.currentTimeMillis()), text));
		if(messages.size() > 42)
			messages.remove();
	}
	
	public static String getText() {
		builder.setLength(0);
		messages.descendingIterator().forEachRemaining(builder::append);
		return builder.toString();
	}
}
