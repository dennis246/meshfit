package web.controller.core;

import java.time.LocalDateTime;
import web.util.FileUtil;

/** 
 * @note experimental
 * @author Dennis Thomas
 */

public class AppController {

    public static void main(String[] args) { 
        startApp(); 
    }

    private static void startApp() {
        prepDefaults();
        ServerController.startHttpServer();
    }

    private static void prepDefaults() {
        //ResourceController.indexPages();
        //new ResourceController().loadRegister();
        // var x = System.getProperties();
    }

    public static void closeAppWithErrorLog(String errorTxt) {
        FileUtil.writeContentToFile(errorTxt, ResourceController.exportPath+"/erorlog_"+LocalDateTime.now());
        System.exit(0);
    }

    
}
