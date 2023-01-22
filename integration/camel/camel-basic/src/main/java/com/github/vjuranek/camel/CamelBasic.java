package com.github.vjuranek.camel;

import org.apache.camel.main.Main;

public class CamelBasic {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new FileToFileRoute(args[0], args[1]));
        main.run();
    }
}
