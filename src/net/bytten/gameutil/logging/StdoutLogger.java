package net.bytten.gameutil.logging;

public class StdoutLogger implements ILogger {

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
    
}
