package com.liang.java8.Nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author Briliang
 * @date 2014/7/22
 * Description()
 */
public class JavaScriptEngine {
    static ScriptEngineManager manager=new ScriptEngineManager();
    static ScriptEngine engine=manager.getEngineByName("JavaScript");

    public static void main(String[] args) {
        System.out.println(engine.getClass().getName());
        try {
            System.out.println("Result:"+engine.eval("function f() { return 1; }; f() + 1;"));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

}
