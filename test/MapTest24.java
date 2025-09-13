package web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import web.util.FileUtil;
import web.util.HarpQueryUtil;
import web.util.MapUtil;

public class MapTest24 {

    public static void main(String[] args) throws Exception {

        var startAt = System.currentTimeMillis();
        // "C:\Users\d3820\Downloads\Junkyard\PeriodicTableJSON.json"
        String floc = "C:\\Users\\d3820\\Downloads\\Junkyard\\PeriodicTableJSON.json";
        String content = FileUtil.convertFileContentToString(floc);

        var resMap = MapUtil.convertToJSONMap(content, 1);
        //HarpQueryUtil.importMapData(resMap);
        HarpQueryUtil.generateEntityClassFile(resMap);
        // var resMapL1 = (LinkedHashMap<String,Object>) resMap.values();
        // var xval = resMapL1.sequencedValues();
        var endAt = System.currentTimeMillis();
        System.out.println("time elapsed: " + ((endAt - startAt) / 1000D));

        // String floc = "D:\\WS-P\\test\\MapUtil2378.txt";
        // // var content = FileUtil.convertFileContentToString(floc);

        // String flocfolder = "D:\\WS-P\\test";
        // File afile = new File(flocfolder);

        // if(afile.exists() && afile.isDirectory()){
        // File[] files = afile.listFiles();

        // for(var file : files){
        // System.out.println(file.getName().);
        // }
        // }
        // String content = null;
        // try {

        // File afile = new File(floc);
        // InputStream is = new FileInputStream(afile);
        // var usp = afile.getUsableSpace();
        // var fsp = afile.getFreeSpace();
        // var tsp = afile.getTotalSpace();

        // var fs = afile.length();
        // var fss = Math.round(afile.length() / 1024.00);

        // var readsize = ((Long) (fs - 1024)).intValue();
        // byte[] revbytes = new byte[readsize];
        // var startfrom = ((Long)Math.round(fs * 0.9)).intValue();
        // var readlen = ((Long)fs).intValue() - startfrom;
        // is.readNBytes(revbytes, startfrom, 10);

        // content = new String(revbytes);
        // //System.out.println(content);

        // String floc2 = "D:\\WS-P\\test\\fdump.txt";
        // FileUtil.writeContentToFile(content, floc2);

        // // for (var i = fs; i > readsize; fs--) {

        // // }

        // revbytes.hashCode();

        // ///byte[] abs = is.readAllBytes();

        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // String floc = "D:\\WS-P\\test\\MapUtil2378.txt";
        // var content = FileUtil.convertFileContentToString(floc);

        // String[] parts = MapUtil.sliceToParts(content, new String[] { "@HarpBased" },
        // new String[] { "public" },
        // SeekPosExclusionCriteria.excludeNone,
        // SeekPosExclusionCriteria.excludeLast,
        // new String[] { }, new String[] {});
        // // var x2 = parts.toString();
        // String[] revisedParts = MapUtil.defrag(parts);

        // // custom filter
        // for(int i=0; i<revisedParts.length; i++){
        // if(revisedParts[i].length() < 20){
        // revisedParts = MapUtil.removeFrom(revisedParts, i);
        // i--;
        // }
        // }

        // String floc2 = "D:\\WS-P\\test\\MapUtil2378_HB.txt";
        // String revisedPartsStr = String.join("\r\n\r\n", revisedParts);
        // FileUtil.writeContentToFile(revisedPartsStr, floc2);

    }
}
