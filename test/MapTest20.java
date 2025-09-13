package web.test;

import java.nio.charset.Charset;

import web.util.HarpQueryUtil;
import web.util.MapUtil;

public class MapTest20 {

    public static void main(String[] args) throws Exception {

        /* var res1 = MathUtil.randomNumberSequence(20);
        System.out.println(res1);*/

       /*  var cs = Charset.availableCharsets();
        for(var crow : cs.entrySet()){
            System.out.println(crow);
        } */

        String contentLocation = "D:\\WS-P\\WS\\G92dfh.harp";
        var contentExtension = contentLocation.substring(contentLocation.indexOf("."));
        String encSchemeLocation = contentLocation.replace(contentExtension, ".eh");
        var res = HarpQueryUtil.EntityDecode(contentLocation, encSchemeLocation);
        System.out.println(res);

        // var res1 = MathUtil.sumOf(new int[]{64, 63, 66, 37, 70, 65, 38, 50, 58});
       /*  String content = FileUtil.convertFileContentToString(contentLocation);
        String dc = new String(MapUtil.distinct(content.toCharArray()));
        System.out.println(dc); */

        /*
         * var res = MathUtil.subdivide_uneven(500, 6, null);
         * for (int i = 0; i < res.length; i++) {
         * System.out.print(res[i] + "\s");
         * }
         * System.out.println("\n"+MathUtil.sumOf(res));
         */

        /*
         * for (int c = 0; c < 10; c++) {
         * var res = MathUtil.subdivide_uneven(25, 4);
         * 
         * for (int i = 0; i < res.length; i++) {
         * System.out.print(res[i] + "\s");
         * }
         * System.out.println();
         * 
         * }
         */

        // 8 4 5 7

        // System.out.println((byte)130);

        /*
         * var arr = StringUtil.alphabet();
         * for(int i=0; i<200; i++){
         * var bv = (byte) i;
         * var iv = (int) i;
         * var cv = (char) i;
         * System.out.println(bv+"-"+iv+"-"+cv);
         * }
         */

    }
}
