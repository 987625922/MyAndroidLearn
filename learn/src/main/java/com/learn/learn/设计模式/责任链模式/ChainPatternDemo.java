package com.learn.learn.设计模式.责任链模式;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/4 17:56
 */
class ChainPatternDemo {
    private static AbstractLogger getChainOfLoggers(){

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger loggerChain = getChainOfLoggers();

//        loggerChain.logMessage(AbstractLogger.INFO, "This is an information.");

//        loggerChain.logMessage(AbstractLogger.DEBUG,
//                "This is a debug level information.");
//
        loggerChain.logMessage(AbstractLogger.ERROR,
                "This is an error information.");
    }
}
