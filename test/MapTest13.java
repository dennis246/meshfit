package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest13 {
     public static void main(String[] args) {

        String query = """
                BEGIN
                INSERT INTO Users
                COLUMNS(UserName, Name, Status, AddedOn)
                VALUES('user741','User 741','01','2025-06-07')
                END
                """;


               

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.INSERT);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);
        qpmap.put("ClassName", "");

        HarpQueryUtil.insert(qpmap);

    }
}
