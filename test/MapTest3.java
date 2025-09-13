package web.test;

import java.util.LinkedHashMap;

import web.model.Item;
import web.util.FileUtil;
import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.HarpQueryUtil.HarpQueryEvalType;
import web.util.HarpQueryUtil.HarpQueryType;
import web.util.MapUtil.SeekPosExclusionCriteria;

public class MapTest3 {

    public static void main(String[] args) {

          String query = 
          """
            BEGIN
            SELECT Brand, B.description, Quantity, code, Status, S.description, addedon FROM
            Items(alias:I; count:1)
            JOIN Brands(alias:B; join:B.code=I.brand)
            JOIN Status(alias:S; join:S.code=I.status)
            WHERE  addedOn > '2025-01-03' and S.description = 'Active'
            END
          """; 

       /*  String query = 
          """
          SELECT code, description, status FROM Brands(count:10) 
          WHERE Code != 'BR02' 
          """; */
    
        //FileUtil.writeContentToFile(query, "D:\\WS-P\\WS\\Query16.txt");
        // var map = Map.of("HarpPropsPath", "D:\\Harp.properties", "query", query);

        LinkedHashMap<String, Object> qpmap = new LinkedHashMap<>(); 
        qpmap.put("HarpPropsPath", "D:\\Harp.properties");
        qpmap.put("Query", query);
        qpmap.put("QueryEvalType", HarpQueryEvalType.MainQuery);
        qpmap.put("QueryType", HarpQueryType.SELECT);
        // qpmap.put("EntityClass", Item.class);

        var queryInfo = (LinkedHashMap<String,Object>) HarpQueryUtil.evaluateQuery(qpmap);
        var resultData = queryInfo.get("DataRows");
        System.out.println(resultData.toString());

    }

}
