package com.management.tpas.utils;

import com.management.tpas.model.UserMsgModel;

/**
 * @description 存储/获取当前线程的用户信息
 **/
public class UserUtil {

    /**
     * 线程变量，存放用户信息
     */
    private static ThreadLocal<UserMsgModel> userThreadLocal = new ThreadLocal<UserMsgModel>();

    /**
     * 获取用户名
     *
     * @return
     */
    public static UserMsgModel getUserMsg() {
        if (userThreadLocal.get() != null) {
            return userThreadLocal.get();
        }
        return null;
    }

    /**
     * 获取请求用户ID，若为NULL，则返回默认值0L
     * @return
     */
    public static Long getUserId() {
        return userThreadLocal.get().getId() == null ? 0L : userThreadLocal.get().getId();
    }

    /**
     * 设置用户名
     *
     * @param userMsgModel
     */
    public static void setUserMsg(UserMsgModel userMsgModel) {
        userThreadLocal.set(userMsgModel);
    }

    /**
     * 清除线程
     */
    public static void removeUserInfo() {
        userThreadLocal.remove();
    }

}
