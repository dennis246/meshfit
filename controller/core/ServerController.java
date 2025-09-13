package web.controller.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import web.controller.scheme.DataScheme;
import web.util.ContainerUtil;
import web.util.FileUtil;
import web.util.MapUtil;
import web.util.StringUtil;

public class ServerController implements Serializable, DataScheme, HttpHandler {

    public static LinkedHashMap<String, LinkedHashMap<String, Object>> serverInfo;

    // static List<LinkedHashMap<String, Object>> appData;

    public ServerController() {
        serverInfo = new LinkedHashMap<>();
        LinkedHashMap<String, Object> metricsMap = new LinkedHashMap<>();
        metricsMap.put("serverState", "started");
        metricsMap.put("incomingRequestsCount", 0);
        metricsMap.put("responsesCount", 0);
        metricsMap.put("requestTimeoutInSeconds", 100);

        serverInfo.put("metrics", metricsMap);

        loadServerInfo();

    }

    public boolean updateServerInfo(int reqType) {

        String reqTypeStr;
        Map serverMetrics = new LinkedHashMap<>();
        if (reqType == 1) {
            reqTypeStr = "GET";

            serverMetrics.put("serverState", "serving: " + reqTypeStr);
            serverMetrics.put("incomingRequestCount", (Integer) serverMetrics.get("incomingRequestCount") + 1);
        } else if (reqType == 2) {
            reqTypeStr = "POST";
            serverMetrics.put("serverState", "serving: " + reqTypeStr);
            serverMetrics.put("responsesCount", (Integer) serverMetrics.get("responsesCount") + 1);
        }

        return true;

    }

    protected static void startHttpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8990), 0);
            server.createContext("/App1", new ServerController());
            server.start();
            System.out.println("Server started");
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            Map<String, Object> requestInfo = new HashMap<>();
            requestInfo.put("exchange", exchange);
            requestInfo.put("URI", exchange.getRequestURI());

            Headers headers = exchange.getRequestHeaders();
            String contentType = null;
            if (headers.get("Content-Type") != null) {
                contentType = headers.get("Content-Type").toString();
                requestInfo.put("Content-Type", contentType);
            }

            if (contentType != null && contentType.equals("application/octet-stream")) {

                InputStream is = exchange.getRequestBody();
                BufferedInputStream bis = new BufferedInputStream(is);

                char[] charArr = new char[0];
                int current = 0;
                while ((current = bis.read()) > 0) {
                    charArr = ContainerUtil.addToCharArray(charArr, (char) current);
                }

                bis.close();
                is.close();

                String incContent = new String(charArr);
                requestInfo.put("content", incContent);
                // System.out.println("\n\n" + incContent);

            } else {
                InputStream is = exchange.getRequestBody();
                String incContent = new String(is.readAllBytes());
                requestInfo.put("content", incContent);
                // System.out.println(incContent);
            }

            // REQUEST HANDLING
            if (exchange.getRequestMethod().equals("GET")) {
                System.out.println("Current Request : GET");
                processGet(requestInfo);
            } else if (exchange.getRequestMethod().equals("POST")) {

                serverInfo.get("exchange");

                System.out.println("Current Request : POST");
                String response = processPost(requestInfo);

               /* added before but DNR exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close(); */

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String processPost(Map requestInfo) {

        String postResponse = "";

        try {

            var xpage = serverInfo.get("currentPage");

            var reqUrl = requestInfo.get("URI").toString();
            String contentType = requestInfo.get("Content-Type").toString();
            String incContent = requestInfo.get("content").toString();

            if (contentType.contains("application/octet-stream")) {
                new ResourceController().forwardRequestDataToController("processUploadedFile", reqUrl,
                        incContent.getBytes());
            } else {

                if (incContent.startsWith("javaCmd")) {
                    new ResourceController().evaluateExpression(incContent);
                } else {
                    new ResourceController().forwardRequestDataToController("processIncoming", reqUrl,
                            incContent.getBytes());
                }

            }

            var serverInfo = ServerController.serverInfo;

            String xpath = requestInfo.get("URI").toString();
            String response = new ResourceController().prepResponse(xpath);
            HttpExchange exchange = (HttpExchange) requestInfo.get("exchange");
            // String host = exchange.getRemoteAddress();
            // System.out.println("current response : \n" + response);
            if (response != null) {
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

            // FileUtil.writeContentToFile(incContent,
            // "D:\\WS-P\\WS\\" + StringUtil.randomIDGenerator(5, true, true, false) +
            // ".txt");
        } catch (Exception e) {
            e.printStackTrace();

        }

        return postResponse;

    }

    private static void processGet(Map<String, Object> requestInfo) throws IOException {

        try {

            var serverInfo = ServerController.serverInfo;

            String xpath = requestInfo.get("URI").toString();
            String response = new ResourceController().prepResponse(xpath);
            HttpExchange exchange = (HttpExchange) requestInfo.get("exchange");
            // String host = exchange.getRemoteAddress();
            // System.out.println("current response : \n" + response);
            if (response != null) {
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

            var raddress = exchange.getLocalAddress().getAddress();
            var rport = exchange.getRemoteAddress().getPort();
            serverInfo.get("exchange");

            if (!userExists(serverInfo, exchange)) {

                // add a new session
                LinkedHashMap<String, Object> userInfoMap = new LinkedHashMap<>();
                String sessionID = generateUserSessionID();
                userInfoMap.put("sessionID", sessionID);
                var addedOn = LocalDateTime.now();
                var setTimeOutAt = addedOn.plusMinutes(5);
                userInfoMap.put("addedOn", addedOn);
                userInfoMap.put("setTimeOutAt", setTimeOutAt);
                userInfoMap.put("actTimeOutAt", null);
                userInfoMap.put("remoteAddress", raddress);
                userInfoMap.put("remotePort", rport);
                userInfoMap.put("currentPage", xpath);

                serverInfo.put("user_" + sessionID, userInfoMap);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String generateUserSessionID() {
        try {
            return "user" + StringUtil.randomIDGenerator(24, true, true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static boolean userExists(LinkedHashMap<String, LinkedHashMap<String, Object>> info,
            HttpExchange exchange) {

        int rport = exchange.getRemoteAddress().getPort();
        var userlist = info.keySet().stream().filter(item -> item.startsWith("user")).collect(Collectors.toList());

        for (var row : userlist) {

            LinkedHashMap<String, Object> rowMap = (LinkedHashMap<String, Object>) info.get(row);
            if (rport == (int) rowMap.get("remotePort")) {
                return true;
            }
        }

        return false;

    }

    public static LinkedHashMap<String, LinkedHashMap<String, Object>> loadServerInfo() {

        try {

            if (serverInfo == null) {
                InputStream is = ServerController.class.getResourceAsStream("/store/common/ServerStore.json");
                if (is != null) {
                    String jsonDataStr = new String(is.readAllBytes());
                    Map<String, Object> serverStoreData = MapUtil.convertToJSONMap(jsonDataStr, 0);
                    serverInfo.get("metrics").put("storeData", serverStoreData);
                    is.close();
                } 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverInfo;
    }

    @Override
    public boolean initiateData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initiateData'");
    }

    @Override
    public boolean loadData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadData'");
    }

    @Override
    public boolean saveData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveData'");
    }

    @Override
    public boolean deleteData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteData'");
    }

}
