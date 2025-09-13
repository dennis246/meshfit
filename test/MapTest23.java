package web.test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import web.util.FileUtil;
import web.util.HarpQueryUtil;
import web.util.MapUtil;
import web.util.StringUtil;

public class MapTest23 {

    public static void main(String[] args) throws Exception {

        // var res = StringUtil.alphabet();
        // for(int i=0; i<res.length; i++){
        //     System.out.print(res[i]+"\s");
        // }
        // var res = StringUtil.removeInvalidLangVarSequences("cpk-hex");
        // System.out.println(res);
    
        var startAt = System.currentTimeMillis();  
        //"C:\Users\d3820\Downloads\Junkyard\PeriodicTableJSON.json"
        String floc = "C:\\Users\\d3820\\Downloads\\Junkyard\\PeriodicTableJSON.json";
        String content = FileUtil.convertFileContentToString(floc);

        var resMap = MapUtil.convertToJSONMap(content, 1);
        HarpQueryUtil.importMapData(resMap);

        // var resMapL1 = (LinkedHashMap<String,Object>) resMap.values();
        // var xval = resMapL1.sequencedValues();
        var endAt = System.currentTimeMillis();
        System.out.println("time elapsed: " + ((endAt - startAt)/1000D));

        

    }
}
