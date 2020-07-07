package com.example.sample;

import com.elvishew.xlog.XLog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 栈异常信息输出工具类
 *
 * @author zhangyonghong
 * @date 2020.4.14
 */
public class ErrorPrintUtil {

    public static void printErrorMsg(Throwable e) {
        printErrorMsg(e, null);
    }

    public static void printErrorMsg(Throwable e, String prefix) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter, true));
        if (prefix != null) {
            XLog.e(prefix + stringWriter.toString());
        } else {
            XLog.e(stringWriter.toString());
        }
    }

}
