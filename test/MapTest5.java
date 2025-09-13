package web.test;

import java.util.LinkedHashMap;

import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest5 {
    public static void main(String[] args) {

        String query = """
                BEGIN
                INSERT INTO Items
                COLUMNS(Description, Status, Code, Brand, Quantity, AddedOn)
                VALUES('An Item of code A0067', '01', 'A00075', 'BR03', 98, '2025-03-06')
                END
                """;

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryType", HarpQueryType.INSERT);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        HarpQueryUtil.insert(qpmap);

    }
}
