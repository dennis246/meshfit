package web.controller.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageProcessor implements Serializable {

    PageProcessor() {
        this.activePageInfo = new ArrayList<>();
    }

    List<Map<String, Object>> activePageInfo;

    public int initPageState(String pageCtrlid) {

        try {
            Class<?> ctrlClass = ClassLoader.getSystemClassLoader().loadClass(pageCtrlid);
            Object ctrlObject = ctrlClass.getDeclaredConstructor().newInstance();
            Method method = ctrlClass.getMethod("initiateView");
            method.invoke(ctrlObject);

            indexPageInvocation(ctrlObject, method, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;

    }

    public static String emitLivePage(String pageCtrlid) {
        try {
            Class<?> ctrlClass = ClassLoader.getSystemClassLoader().loadClass(pageCtrlid);
            Object ctrlObject = ctrlClass.getDeclaredConstructor();
            Method method = ctrlClass.getMethod("emitLivePage");
            Object result = method.invoke(ctrlObject);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void indexPageInvocation(Object ctrlObject, Method method, Object params) {

        LinkedHashMap<String, Object> currentClassInfo = new LinkedHashMap<>();
        currentClassInfo.put("objClassName", ctrlObject.getClass().getName());
        currentClassInfo.put("objMethodName", method.getName());
        currentClassInfo.put("objMethodParams", params);
        currentClassInfo.put("addedOn", LocalDateTime.now());
        currentClassInfo.put("objState", ctrlObject);

        ServerController.serverInfo.put(ctrlObject.getClass().getName(), currentClassInfo);

        // valid for single user only
        // var reqCount = (Integer)
        // ServerController.serverInfo.get("metrics").get("incomingRequestsCount");

    }

    public void invocationHistory() {

    }

    public static String execute(String pageCtrlid, String implMethodName, byte[] methodParams) {
        try {

            var serverInfo = ServerController.serverInfo;

            var targetObjInfo = (LinkedHashMap<String, Object>) serverInfo.get(pageCtrlid);
            var currentObject = targetObjInfo.get("objState");

            Class<?> ctrlClass = ClassLoader.getSystemClassLoader().loadClass(pageCtrlid);
            // Object ctrlObject = ctrlClass.getDeclaredConstructor().newInstance();
            Object ctrlObject = currentObject;
            Method method = ctrlClass.getMethod(implMethodName, methodParams.getClass());
            Object result = method.invoke(ctrlObject, methodParams);

            if (result != null && result instanceof String) {
                return result.toString();
            } else if (result != null && result instanceof Boolean) {
                var resOp = (Boolean) result;
                if (resOp) {
                    return "200";
                } else {
                    return "500";
                }

            } else {
                return "200";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int inspectPageState(String pagename, String pageCtrlid) {
        try {
            Class<?> ctrlClass = ClassLoader.getSystemClassLoader().loadClass(pageCtrlid);
            Object ctrlObject = ctrlClass.getDeclaredConstructor();
            Object ctrlObjectField = Arrays.stream(ctrlClass.getFields())
                    .filter(item -> item.getName().equals("pagename" + "Container")).findFirst();

            if (ctrlObjectField != null) {
                return 1;
            }

            Method method = ctrlClass.getMethod("initiateView");
            method.invoke(ctrlObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

}
