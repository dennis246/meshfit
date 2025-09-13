package web.test;

import web.util.FileUtil;
import web.util.MapUtil;
import web.util.StringUtil;
import web.util.HarpQueryBuilder;
import web.util.HarpQueryUtil;

public class MapTest28 {

    public static void main(String[] args) {

        String template = """
                package web.test;

                import web.util.FileUtil;
                import web.util.MapUtil;
                import web.util.StringUtil;

                class MapTest29 {

                public static void main(String[] args) {
                            #{param}
                                        """;

        char[] ax = new char[0];
        ax = StringUtil.addToCharArray(ax, (char) 92);
        ax = StringUtil.addToCharArray(ax, (char) 92);
        ax = StringUtil.addToCharArray(ax, (char) 92);

        String concatsection = "";

        int max = 100;
        for (int i = 0; i < max; i++) {

            String cix = Integer.valueOf(i).toString();

            if (cix.toCharArray()[0] == '8' || cix.toCharArray()[0] == '9') {
                continue;
            }

            // String cseq = """
            // System.out.println("#{rseq}"+"#{indexpart}");
            // """.replace("#{rseq}", new String(ax) + Integer.valueOf(i).toString()) +
            // "\r";

            // template = template.replace("#{param}", cseq + "\r").replace("#{indexpart}",
            // ":" + Integer.valueOf(i).toString());
            concatsection += "ax" + cix;

            String cseq = "var ax#{indexpart}\s=\s\"#{rseq}\";"
                    .replace("#{indexpart}", Integer.valueOf(i).toString())
                    .replace("#{rseq}", new String(ax) + Integer.valueOf(i).toString() + "\r");

            template = template.replace("#{param}", cseq);

            if (i < max - 1) {
                template += "#{param}";
                concatsection += "+";
            } else {
                concatsection += ";";
            }

        }

        if (concatsection.length() > 0) {

            template += "String ccs = " + concatsection + ";\r\n\r\n";

        }

        template += """

                var apploc = System.getProperty("user.dir");
                var packageFolder = "\\\\web\\\\test\\\\";
                var packagePath = packageFolder + "MapTest29";
                var packageName = packageFolder.replace("\\\\", "\s").trim().replace("\s", ".");
                var EntityClassPath = apploc + packagePath + ".java";

                String cfBuilder = MapUtil.formatCodeBlock(MapUtil.linearizeContent(template));
                FileUtil.writeContentToFile(cfBuilder, EntityClassPath);
                             """;

        template += " }\r\n}";

        // template = template.replace("#{param}", "\r");
        // System.out.println(template);

        var apploc = System.getProperty("user.dir");
        var packageFolder = "\\web\\test\\";
        var packagePath = packageFolder + "MapTest29";
        var packageName = packageFolder.replace("\\", "\s").trim().replace("\s", ".");
        var EntityClassPath = apploc + packagePath + ".java";

        String cfBuilder = MapUtil.formatCodeBlock(MapUtil.linearizeContent(template));
        FileUtil.writeContentToFile(cfBuilder, EntityClassPath);

    }

}
