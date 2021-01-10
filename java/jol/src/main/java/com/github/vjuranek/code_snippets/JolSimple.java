package com.github.vjuranek.code_snippets;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JolSimple {
    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(ClassWithBoolean.class).toPrintable());
        System.out.println(ClassLayout.parseClass(ClassWithLong.class).toPrintable());
        System.out.println(ClassLayout.parseClass(ClassWithBooleanAndLong.class).toPrintable());
        System.out.println(ClassLayout.parseClass(ClassWithString.class).toPrintable());
        System.out.println(ClassLayout.parseClass(String.class).toPrintable());
    }
    
    public static class ClassWithBoolean {
        boolean f;
    }

    public static class ClassWithLong {
        Long f;
    }

    public static class ClassWithBooleanAndLong {
        Boolean b;
        Long f;
    }
    
    public static class ClassWithString {
        String f;
    }
}
