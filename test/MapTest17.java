package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;

public class MapTest17 {

    public static void main(String[] args) throws Exception {

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("EntityClassName", "Brands");
        qpmap.put("EntityName", "Brands");
        HarpQueryUtil.generateEntityClassFile(qpmap); 


    }

}
