package web.test;

import web.util.MapUtil;
import web.util.MapUtil.MatchCriteria;
import web.util.MapUtil.SeekPosExclusionCriteria;

public class MapTest4 {

    public static void main(String[] args) throws Exception {

        String frsh = "A0001,Item 1009292,BR02,2025-01-04T12:30:04:001,01,31,,";
        String text = "particle physics";
        //System.out.println(text.substring(2, 2));
        //System.out.println(MapUtil.extractByIndexRange(text.toCharArray(), 2, 2));
        //String text2 = "b";
        String op2 = MapUtil.replaceWith(text, "particle", "chemistry");
        System.out.println(op2);
       /*  var dstr = "2025-01-05";
        var op = MapUtil.parseDate(dstr);
        System.out.println(op); */

        // var op = MapUtil.hasChars(text.toCharArray(), text2.toCharArray(), 1);
        // var op = MapUtil.startsWith(text.toCharArray(), text2.toCharArray(), 0);
        // var op = MapUtil.endsWith(text.toCharArray(), text2.toCharArray(), 0);
        // var op = MapUtil.checkGroupSeq(text.toCharArray(), 0, new char[][] {
        // text2.toCharArray() });

       /*  String co = """
                Brands(alias:B; join:B.code=I.brand) \r
                """; */
        //var op = MapUtil.trim(co.toCharArray());
        //var op2 = new String(op);

       /*  String queryBlock = """
                  BEGIN
                  SELECT Brand, B.description, Quantity, code, S.description, addedon FROM
                  Items(alias:I; count:2)
                  JOIN Brands(alias:B; join:B.code=I.brand)
                  JOIN Status(alias:S; join:S.code=I.status)
                  END
                """;

        var slicop = MapUtil.sliceToParts(queryBlock, new String[] { "JOIN" },
                new String[] { "FROM", "JOIN", "END" },
                SeekPosExclusionCriteria.excludeFirst,
                SeekPosExclusionCriteria.excludeLast, new String[] { "\r\n\s" }, new String[] { "" });
        System.out.println(slicop);
 */
        /*DNR 
         * var op1 = MapUtil.extractContentWithinBounds(queryBlock, 0, new String[] {
         * "JOIN" },
         * new String[] { "JOIN" },
         * new String[] { "JOIN", "END", "GROUP BY" },
         * SeekPosExclusionCriteria.excludeFirst,
         * SeekPosExclusionCriteria.excludeLast,
         * new String[] { "\r\n" },
         * 0);
         * 
         * char[] queryBlockArr = queryBlock.toCharArray();
         * // MapUtil mu = new MapUtil();
         * var utilInfo = MapUtil.checkForExactMatchFromBlock(queryBlockArr,
         * "JOIN".toCharArray(), MatchCriteria.All);
         * 
         * var startPtsArr = (int[]) utilInfo.get("startPoints");
         * var endPtsArr = (int[]) utilInfo.get("endPoints");
         * 
         * var cstartPt = -1;
         * var cendPt = -1;
         * for (int i = 0; i < startPtsArr.length; i++) {
         * 
         * cstartPt = endPtsArr[i];
         * if (i + 1 <= endPtsArr.length - 1) {
         * cendPt = startPtsArr[i + 1];
         * } else {
         * cendPt = queryBlockArr.length - 1;
         * }
         * 
         * var opstr = MapUtil.extractByIndexRange(queryBlockArr, cstartPt + 1, cendPt -
         * 1, new char[] { '\s' });
         * System.out.println(opstr);
         * 
         * }
         * 
         */

    }

}
