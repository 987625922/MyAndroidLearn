package com.library.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * This is a Log tool，with this you can the following
 * <ol>
 * <li>use KLog.d(),you could print whether the method execute,and the default tag is current class's name</li>
 * <li>use KLog.d(msg),you could print log as before,and you could location the method with a click in Android Studio Logcat</li>
 * <li>use KLog.json(),you could print json string with well format automatic</li>
 * </ol>
 *
 * @author zhaokaiqiang
 * github https://github.com/ZhaoKaiQiang/KLog
 * 15/11/17 扩展功能，添加对文件的支持
 * 15/11/18 扩展功能，增加对XML的支持，修复BUG
 * 15/12/8  扩展功能，添加对任意参数的支持
 * 15/12/11 扩展功能，增加对无限长字符串支持
 * 16/6/13  扩展功能，添加对自定义全局Tag的支持,修复内部类不能点击跳转的BUG
 * 16/6/15  扩展功能，添加不能关闭的KLog.debug(),用于发布版本的Log打印,优化部分代码
 */
public final class KLog {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String NULL_TIPS = "Log with null object";
    private static final String DEFAULT_MESSAGE = "execute";
    private static final String TAG_DEFAULT = "KLog";
    private static final String FILE_PREFIX = "KLog_";
    private static final String FILE_FORMAT = ".log";

    private static final int MAX_LENGTH = 4000;
    private static final int JSON_INDENT = 4;
    private static final int STACK_TRACE_INDEX_5 = 5;

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    private static final int A = 0x6;
    private static final int JSON = 0x7;
    //XML=JSON+E
    private static final int XML = 0xC;

    private static String globalTag;
    private static boolean isShowLog = true;

    /*
     * init
     * */
    public static void init(boolean isShowLog) {
        KLog.isShowLog = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        KLog.isShowLog = isShowLog;
        globalTag = TextUtils.isEmpty(tag) ? TAG_DEFAULT : tag;
    }

    /*
     * base
     * */
    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, msg);
    }

    public static void v(String tag, Object objects) {
        printLog(V, tag, objects);
    }

    public static void d() {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, Object objects) {
        printLog(D, tag, objects);
    }

    public static void i() {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, msg);
    }

    public static void i(String tag, Object objects) {
        printLog(I, tag, objects);
    }

    public static void w() {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, msg);
    }

    public static void w(String tag, Object objects) {
        printLog(W, tag, objects);
    }

    public static void e() {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, Object objects) {
        printLog(E, tag, objects);
    }

    public static void a() {
        printLog(A, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(A, null, msg);
    }

    public static void a(String tag, Object objects) {
        printLog(A, tag, objects);
    }

    public static void json(int level, String jsonFormat) {
        printLog(JSON + level, null, jsonFormat);
    }

    public static void json(int level, String tag, String jsonFormat) {
        printLog(JSON + level, tag, jsonFormat);
    }

    public static void xml(int level, String xml) {
        printLog(XML + level, null, xml);
    }

    public static void xml(int level, String tag, String xml) {
        printLog(XML + level, tag, xml);
    }

    public static void map(int level, Map<String, Object> map) {
        printLog(JSON + level, null, map);
    }

    public static void mapString(int level, String tag, Map<String, String> map) {
        printLog(JSON + level, tag, map);
    }

    public static void map(int level, String tag, Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            printLog(level, tag, null);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        printLog(JSON + level, tag, jsonObject.toString());
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    public static void debug() {
        printDebug(null, DEFAULT_MESSAGE);
    }

    public static void debug(Object msg) {
        printDebug(null, msg);
    }

    public static void debug(String tag, Object objects) {
        printDebug(tag, objects);
    }

    /*
     * log内容处理
     * */
    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object objects) {
        StackTraceElement targetElement = Thread.currentThread().getStackTrace()[stackTraceIndex];
        String className = targetElement.getFileName();
        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = TextUtils.isEmpty(tagStr) ? globalTag : tagStr;
        String msg = (objects == null) ? NULL_TIPS : objects.toString();
        String headString = "[(" + className + ":" + lineNumber + ")#" + methodName + "] ";

        return new String[]{tag, msg, headString};
    }

    /*
     * log输出
     * */
    private static void printLog(int type, String tagStr, Object objects) {
        if (!isShowLog) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                checkLenPrint(type, tag, headString + msg);
                break;
            case JSON + V:
            case JSON + D:
            case JSON + I:
            case JSON + W:
            case JSON + E:
                printJson(type, tag, msg, headString);
                break;
            case XML + V:
            case XML + D:
            case XML + I:
            case XML + W:
            case XML + E:
                printXml(type, tag, msg, headString);
                break;
        }
    }

    private static void printDebug(String tagStr, Object objects) {
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        checkLenPrint(D, tag, headString + msg);
    }

    /**
     * 检查长度，如果超出MAX_LENGTH则分段输出
     *
     * @param type
     * @param tag
     * @param msg
     */
    public static void checkLenPrint(int type, String tag, String msg) {
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub == 0) {
            //length<MAX_LENGTH
            print(type, tag, msg);
            return;
        }
        //length>MAX_LENGTH
        int index = 0;
        for (int i = 0; i < countOfSub; i++) {
            String sub = msg.substring(index, index + MAX_LENGTH);
            print(type, tag, sub);
            index += MAX_LENGTH;
        }
        print(type, tag, msg.substring(index, length));
    }

    private static void print(int type, String tag, String sub) {
        switch (type) {
            case KLog.V:
                Log.v(tag, sub);
                break;
            case KLog.D:
                Log.d(tag, sub);
                break;
            case KLog.I:
                Log.i(tag, sub);
                break;
            case KLog.W:
                Log.w(tag, sub);
                break;
            case KLog.E:
                Log.e(tag, sub);
                break;
            case KLog.A:
                Log.wtf(tag, sub);
                break;
        }
    }

    private static void printJsonXml(int level, String tag, String message, String headString) {
        print(level, tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");

        message = headString + KLog.LINE_SEPARATOR + message;

        if (message.length() > MAX_LENGTH) {
            checkLenPrint(level, tag, message);
        } else {
            String[] lines = message.split(KLog.LINE_SEPARATOR);
            for (String line : lines) {
                print(level, tag, "║ " + line);
            }
        }

        print(level, tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

    /*
     * file
     * */

    /**
     * @param tagStr
     * @param targetDirectory
     * @param fileName
     * @param objectMsg
     */
    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {
        if (!isShowLog) {
            return;
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        printFile(tag, targetDirectory, fileName, headString, msg);
    }

    /**
     * @param tag
     * @param targetDirectory
     * @param fileName
     * @param headString
     * @param msg
     */
    public static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = FILE_PREFIX + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(System.currentTimeMillis()) + FILE_FORMAT;
        }
        if (save(targetDirectory, fileName, msg)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else {
            Log.e(tag, headString + "save log fails !");
        }
    }

    /**
     * @param dic
     * @param fileName
     * @param msg
     * @return
     */
    private static boolean save(File dic, @NonNull String fileName, String msg) {
        File file = new File(dic, fileName);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * json
     * */
    public static void printJson(int type, String tag, String msg, String headString) {
        String message;

        try {
            if (msg.startsWith("{")) {
                message = new JSONObject(msg).toString(KLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                message = new JSONArray(msg).toString(KLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printJsonXml(type - JSON, tag, message, headString);
    }

    /*
     * xml
     * */
    public static void printXml(int type, String tag, String xml, String headString) {
        xml = TextUtils.isEmpty(xml) ? KLog.NULL_TIPS : formatXML(xml);
        printJsonXml(type - XML, tag, xml, headString);
    }

    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }
}