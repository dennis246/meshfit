package web.test;

import java.util.LinkedHashMap;
import java.util.Map;

import web.model.Customer;
import web.model.Item;
import web.util.FileUtil;
import web.util.HarpQueryUtil;
import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;

public class MapTest2 {
    public static void main(String[] args) {

      /*   String query = """
                BEGIN
                SELECT * FROM Items WHERE Code = 'A0004' AND Status = '01'
                END
                    """; */

                String query = """
                    BEGIN
                    SELECT * FROM Customer
                    WHERE id = 'C0022738'
                    END  
                    """;

        //FileUtil.writeContentToFile(query, "D:\\WS-P\\WS\\Query16.txt");
        // var map = Map.of("HarpPropsPath", "D:\\Harp.properties", "query", query);

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>();
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("EntityClass", Customer.class);
        qpmap.put("QueryType", HarpQueryType.SELECT);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);

        var res = (LinkedHashMap<String,Object>) HarpQueryUtil.evaluateQuery(qpmap);
        res.keySet();

    }
}
