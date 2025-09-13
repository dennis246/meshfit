package web.test;

import web.util.HarpQueryUtil;
import web.util.MapUtil;

public class MaptTest19 {
  public static void main(String[] args) throws Exception {

    /* char[] charArr = new char[0];
    for (int i = 1; i < 500; i++) {

      var cchar = (char) i;
      charArr = MapUtil.addToCharArray(charArr, cchar);

    } */

    String contentLocation = "D:\\WS-P\\WS\\G92dfh.harp";
    HarpQueryUtil.EntityEncode(contentLocation);
    System.out.println("end");

    /*
     * Brands[] brandsArr = new Brands[0];
     * for (int i = 1; i < 10; i++) {
     * 
     * Brands brand = new Brands();
     * brand.setCode("A" + i);
     * brand.setDescription("BrandA" + i);
     * brandsArr = MapUtil.add(brandsArr, brand);
     * 
     * }
     * 
     * 
     * var res = MapUtil.shuffle(brandsArr);
     * 
     * for (var crow : res) {
     * System.out.print(crow.getCode()+"-"+crow.getDescription() + "\s");
     * }
     * System.out.println("\n");
     */

    /*
     * for (int i = 0; i < 10; i++) {
     * String[] charArr = { "next", "hydrogen", "atom", "but", "fix", "kept",
     * "with", "carboxyl", "group" };
     * var res = MapUtil.shuffle(charArr);
     * 
     * for (var crow : res) {
     * System.out.print(crow + "\s");
     * }
     * System.out.println("\n");
     * 
     * // var disres = MapUtil.distinct(charArr);
     * // System.out.println(res.length + " x " + disres.length);
     * }
     */

    /*
     * var sumArr = new String[0];
     * var nt = 0;//System.currentTimeMillis();
     * for (int i = 0; i < 20; i++) {
     * ++nt;
     * //var x1Arr = MathUtil.convertToIntArray(Long.valueOf(nt).toString());
     * // var x1Sum = Math.abs(MathUtil.subtractionOf(x1Arr));
     * var x1ArrStr = String.valueOf(nt);
     * System.out.println(x1ArrStr);
     * sumArr = MapUtil.add(sumArr, String.valueOf(x1ArrStr));
     * 
     * }
     * 
     * var disSumArr = MapUtil.distinct(sumArr);
     * System.out.println(sumArr.length + " x " + disSumArr.length);
     */

    /*
     * {
     * var x1 = "91379447016900";
     * var x1Arr = MathUtil.convertToIntArray(x1);
     * var x1Sum = MathUtil.productOf(x1Arr);
     * System.out.println(x1Sum);
     * }
     * 
     * {
     * var x1 = "91373079645600";
     * var x1Arr = MathUtil.convertToIntArray(x1);
     * var x1Sum = MathUtil.productOf(x1Arr);
     * System.out.println(x1Sum);
     * }
     */

    /*
     * var nt = System.nanoTime();
     * System.err.println(nt);
     * 
     * var ntStr = String.valueOf(nt);
     * var ntArr = ntStr.toCharArray();
     * 
     * int sum = 0;
     * for(int i=0; i<ntArr.length; i++){
     * sum+=ntArr[i];
     * }
     * System.err.println(sum);
     * System.err.println("end");
     */

    /*
     * String pw = "protein_synthesis";
     * var res = new String(MapUtil.shuffle(pw.toCharArray()));
     * System.out.println(res);
     */

    /*
     * char x = '‘';
     * int xv = (int) x;
     * 
     * String xx = "‘";
     * String xcv = new String(xx.getBytes(), Charset.forName("UTF-8"));
     * System.out.println(xcv);
     */

    // char[] wk = new char[]{'a','f','a','e','k','5','6','r','r','o'};
    // var res = MapUtil.distinct(new String[]{"200","30","11","200","40","70"});
    // System.out.println(res);

    /*
     * String encFg = StringUtil.randomIDGenerator(20, true, false, false);
     * System.out.println(encFg);
     */

    /*
     * String fc = "";
     * char[] fcArr = new char[0];
     * var startFrom = 6000;
     * for (int i = startFrom; i < startFrom+1000; i++) {
     * fcArr = MapUtil.addToCharArray(fcArr, (char) i);
     * }
     * 
     * var entrySet = Charset.availableCharsets().entrySet();
     * 
     * for(var crow : entrySet){
     * System.err.println(crow.getValue());
     * }
     */

    /* 
      */

    // FileUtil.writeContentToFile(new String(fcArr),
    // "D:\\\\WS-P\\\\WS\\\\Ucset.txt",
    // Charset.forName("UTF-16"));
  }
}
