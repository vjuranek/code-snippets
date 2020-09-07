package com.github.vjuranek.code_snippets;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class SimpleExample {
    public static void main(String[] args) throws Exception {
	try (var e  = Executors.newVirtualThreadExecutor()) {
	    List<Callable<String>> tasks = List.of(
	        () -> "a",
		() -> "b",
		() -> "c",
		() -> "d",
		() -> "e"
	    );
	    String first = e.invokeAny(tasks);
	    System.out.println("First: " + first);
	}
    }
}
