package web.controller.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import web.util.ContainerUtil;
import web.util.FileUtil;
import web.util.MapUtil;

/* @Tagged(tag = "resCtrl") */
public class ResourceController implements Serializable {

    public static LinkedHashMap<String, Object> register;

    public static final String storeMode = "Harp";
    // public static final String storeMode = "ObjectNotation";

    public Map<String, String> props;

    public Map<String, String> params;

    public static final String contextPath = System.getProperty("user.dir").replace("\\", "//");

    public static final String resourcesPath = contextPath + "/web/view/resources";

    public static final String storePath = contextPath + "/web/store/common";

    public static final String exportPath = contextPath + "/web/store/exported";

    public static final String importPath = contextPath + "/web/store/imported";

    public static final String libPath = contextPath + "/web/lib";

    public static final String registryPath = contextPath + "/web/controller/core/Registry.json";

    public static final String HarpPropsPath = "D:\\Harp.properties";

    public String currentResponse;

    public enum ResourceType {
        html, json, xml, pdf, docx, xls, png, jpg, mp3, mp4, avi, mpeg
    }

    public ResourceController() {
        resetCounters();
        // loadRegister();
    }

    private void resetCounters() {
        register = new LinkedHashMap<>();
    }

    public String findResource() {

        return errorResponseContent("404", "resource not found");
    }

    public String prepResponse(String xpath) {

        try {

            String resource = resolvePath(xpath);
            if (resource != null) {
                String resContent = prepResponseContentFor(resource, ResourceType.html);
                if (resContent != null) {
                    register.put("currentResourceName", resource);
                    return resContent;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return errorResponseContent("404", "resource not found");
    }

    private static String prepResponseContentFor(String resource, ResourceType resourceType) {

        String content = FileUtil.convertFileContentToString(resource);

        if (content != null) {
            return content;
        } else {
            return errorResponseContent("404", "resource not found");
        }

    }

    private static String errorResponseContent(String errorcode, String message) {

        String defaultErrorResp = """
                <html>
                <body style="text-align:center;font-family:Consolas;">
                <h2 style="color:red;" >#{errorcode}</h2>
                <p>
                #{message}
                </p>
                </body>
                </html>
                """
                .replace("#{errorcode}", errorcode)
                .replace("#{message}", message != null ? message : "resource not found");

        /*
         * if (errorcode.equals("404", )) {
         * return defaultErrorResp;
         * }
         */

        return defaultErrorResp;
    }

    @SuppressWarnings("unchecked")
    private String resolvePath(String xpath) throws Exception {

        String pageLocation = "";
        if (xpath.startsWith(AppInfo.entityContext)) {
            // valid
            String pageName = xpath.substring(AppInfo.entityContext.length());

            if (register == null || register.isEmpty()) {
                loadRegister();
            }
            // evaluate page
            LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) register
                    .get(pageName.substring(1));
            if (resultMap != null) {

                // prep page data
                String cpage = resultMap.get("controller").toString();
                // var serverInfo = ServerController.getServerInfo();
                // inspectPageState(resultMap);
                new PageProcessor().initPageState(MapUtil.removeQuotes(cpage));
            }

            pageLocation = contextPath + resultMap.get("location")
                    .toString().replace("templates", "live")
                    .replace(".html", "_live.html");

            ServerController.serverInfo.put("currentPage", resultMap);

            if (pageLocation != null) {

                // remove previous
                /*
                 * File aFile = new File(pageLocation);
                 * if (aFile.exists()) {
                 * aFile.delete();
                 * }
                 */

            }
        } else {
            throw new Exception("Invalid Request Path");
        }

        return pageLocation;

    }

    private void inspectPageState(LinkedHashMap<String, String> resultMap) {

        int ps = PageProcessor.inspectPageState(resultMap.get("name"), resultMap.get("controller"));
        if (ps == 1) {
            ServerController.serverInfo.get("metrics").put("pageState", "active");
        }

    }

    protected void loadRegister() {

        var usersList = ServerController.serverInfo.keySet()
                .stream().filter(item -> item.startsWith("user_")).toList();

        for (var user : usersList) {
            var regMap = (LinkedHashMap) ServerController.serverInfo.get(user).get("register");

            if (regMap != null) {
                register = regMap;
            }
            // TODO: now single user for multiple an identfier is required.
        }

        if ((Integer) ServerController.serverInfo.get("metrics")
                .get("incomingRequestsCount") > 0) {

        } else {

            System.out.println("load register FF");
            register = new LinkedHashMap<>();
            String registryStored = FileUtil.convertFileContentToString(ResourceController.registryPath);
            // MapUtil mapUtil = new MapUtil();

            var tempMap = MapUtil.convertToJSONMap(registryStored, 1);
            var finalMap = MapUtil.removeAllQuotes(tempMap);

            if (finalMap != null && !finalMap.isEmpty()) {
                setRegister(finalMap);
            }

            // save to serverinfo
            ServerController.serverInfo.put("register", register);
            register.get("page");
        }

    }

    protected void prepProps() {
        props = new LinkedHashMap<>();
        props.put("contextPath", System.getProperty("user.dir"));
    }

    public String findPagePath(String pageid) {

        if (register == null || register.isEmpty()) {
            loadRegister();
        }

        LinkedHashMap<String, String> pageMap = (LinkedHashMap<String, String>) register.get(pageid);

        if (pageMap != null) {
            return pageMap.get("location").toString();
        }

        return null;

    }

    public static void openStore(String entity) {

        // File afile = new File(storePath + "/" + entity);

    }

    public String defaultPageLocation(String pagename) {
        String pagePath = findPagePath(pagename);
        String qualifiedPagePath = ResourceController.contextPath + pagePath.replace("\"", "");
        return qualifiedPagePath;
    }

    public String defaultEntityStoreLocation(String entityName) {
        return storePath + "/" + entityName + "Store.json";
    }

    public String load(String entity) throws Exception {
        String dataLocation = storePath + "/" + entity + "Store.json";
        File afile = new File(dataLocation);
        if (!afile.exists()) {
            throw new FileNotFoundException("Page Store not found");
        }

        return FileUtil.convertFileContentToString(dataLocation);
    }

    private String createEntity(String entity) {

        String entityObj = """
                 {
                    "#{entity}" : {}
                 }
                """.replace("#{entity}", entity);
        return entityObj;
    }

    /*
     * public static String prepLivePage(Map<String, Object> container) {
     * 
     * try {
     * 
     * // update part of page
     * String pagename = container.get("id").toString();
     * String pagePath = new ResourceController().findPagePath(pagename);
     * String qualifiedPagePath = ResourceController.contextPath +
     * pagePath.replace("\"", "");
     * 
     * if (container.get("pageOutputNature").toString().equals("dynamic")) {
     * String livePath = qualifiedPagePath.replace("templates", "live")
     * .replace(".html", "_live.html");
     * String pageTemplate = FileUtil.convertFileContentToString(qualifiedPagePath);
     * 
     * String pageResponse = updatePageProperties(pageTemplate, container);
     * FileUtil.writeContentToFile(pageResponse, livePath);
     * System.out.println(pagename + " page reloaded");
     * return livePath;
     * }
     * 
     * } catch (Exception e) {
     * System.err.println(e.toString());
     * }
     * 
     * return null;
     * 
     * }
     */

    private static String updatePageProperties(String pageContent, Map<String, Object> container) {

        // container.put("appContextPath", contextPath);
        pageContent = pageContent.replace("#{appContextPath}", contextPath);

        for (var row : container.keySet()) {
            if (row.startsWith("component")) {
                String cap = "#{" + container.get("placeHolder").toString() + "." + row + "}";

                String repVal = "";
                Object repValObj = container.get(row);
                if (repValObj instanceof byte[]) {
                    repVal = new String((byte[]) repValObj);
                } else {
                    repVal = repValObj.toString();
                }

                // System.out.println("render: \n\n" + repVal);
                pageContent = pageContent.replace(cap, repVal);
            }
        }

        // System.out.println("render: \n\n" + pageContent);
        return pageContent;

    }

    public LinkedHashMap<String, Object> getRegister() {
        return register;
    }

    public void setRegister(LinkedHashMap<String, Object> register) {
        this.register = register;
    }

    public static Map<String, String> processPostContent(String incContent) throws Exception {

        String[] parts = incContent.replace("\s", "").split("\n");

        parts = ContainerUtil.remove(parts, 0);

        Map<String, String> resMap = new LinkedHashMap<>();
        for (var part : parts) {

            String[] subparts = part.split("=");
            resMap.put(subparts[0], subparts[1]);
        }

        return resMap;

    }

    public void evaluateExpression(String incContent) {

        try {

            String jarpath = libPath + "/Util241.jar";
            var cmdSeq = Arrays.asList("cmd.exe", "/c", "java -jar " + jarpath);
            ProcessBuilder pb = new ProcessBuilder(cmdSeq);
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void forwardRequestDataToController(String implMethod, String reqURL, byte[] reqBytes) {

        try {

            if (reqBytes == null || reqBytes.length == 0) {
                System.err.println("no data");
                return;
            }

            // ServerController.serverInfo.get("current").get("currentPage");
            if (register == null || register.isEmpty()) {
                loadRegister();
            }

            // var serverInfo = ServerController.serverInfo.get("currentPage");
            // Map<String, String> currentPageMap = (Map<String, String>)
            // register.get("currentPage");
            Map<String, Object> currentPageMap = (Map<String, Object>) ServerController.serverInfo.get("currentPage");
            PageProcessor.execute(currentPageMap.get("controller").toString(), implMethod, reqBytes);

        } catch (Exception e) {
            System.err.println("from Resource Controller");
            e.printStackTrace();
        }
    }

    public String loadFromHarp(String entity) {

        String dataLocation = "";

        try {

            {
                File afile = new File(HarpPropsPath);
                if (afile.exists()) {
                    InputStream is = new FileInputStream(afile);
                    Properties props = new Properties();
                    props.load(is);
                    is.close();

                    String HarpLoc = props.getProperty("HarpPath");
                    dataLocation = HarpLoc.replace("\\", "/");
                }

            }

            dataLocation = dataLocation + "/" + entity + ".Harp";
            File afile = new File(dataLocation);
            if (!afile.exists()) {
                throw new FileNotFoundException("Page Store not found");
            }

            return FileUtil.convertFileContentToString(dataLocation);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
