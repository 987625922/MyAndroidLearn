package com.ocwvar.lib_floatingassistant.Utils;

import android.os.Handler;
import android.text.TextUtils;

import com.library.util.RxUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RobotControlUtils {
    private static long time = 1 * 1000; //机器人前进后几秒停止
    private static final String GO = "go";
    private static final String BACK = "back";
    private static final String STOP = "stop";
    private static String filePath = "/sys/elink/td702_2_led";

    static Thread thread = new Thread(() -> {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Stop();
    });

    public static void Go() {
        writeFile(GO);
        thread.start();
    }

    public static void Back() {
        writeFile(BACK);
    }

    public static void Stop() {
        writeFile(STOP);
    }

    private static void writeFile(String State) {
        try {
            if (TextUtils.isEmpty(State)) {
                return;
            }
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(
                    filePath));
            bufWriter.write(State + "");
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
