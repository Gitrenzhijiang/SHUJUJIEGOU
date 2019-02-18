package sqlutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaCreater {

    public static void main(String[] args) {
        // 拿到sqlFileScanner生成的中间文件
        File midFile = new File("D:\\myfile\\项目\\暑假项目\\需求分析\\database\\temp.txt");
        File destDir = new File("C:\\Users\\Administrator\\Desktop\\Test\\bigdatapig\\src\\main\\java\\cn\\bluedot\\core\\domain\\");
        new JavaCreater().jcreate(midFile, destDir, "cn.bluedot.core.domain");
    }
    public void jcreate(File midFile, File destDir, String packageName) {
        if (!destDir.isDirectory()) {
            throw new IllegalArgumentException("目标文件必须是一个文件夹!");
        }
        BufferedReader br = null;
        List<Domain> buff = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(midFile), "UTF-8"));
            String line = null;
            String domainName = null;// 文件名
            File javafile = null;// 目标文件
            BufferedWriter bw = null;// 当前目标文件输入
            Domain domain = null;
            List<String> list = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("===")) {
                    if (domainName != null) {
                        // 上一次的bw, javafile, domainName
                        buff.add(domain);
                    }
                    domain = new Domain();
                    list = new ArrayList<>();
                    domainName = line.replace("===", "");
                    domainName = domainName.substring(0, 1).toUpperCase() + domainName.substring(1);
                    javafile = new File(destDir, domainName+".java");
                    if (javafile.exists()) {
                        javafile.delete();
                    }
                    javafile.createNewFile();
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javafile), "UTF-8"));
                    bw.write("package " + packageName + ";");
                    bw.newLine();
                    bw.write("public class " + domainName + "{");
                    bw.newLine();
                    domain.list = list;
                    domain.bw = bw;
                    domain.dest = javafile;
                }else if (!line.equals("")){
                    String[] ss = line.split("\t");
                    
                    if (ss.length == 3) {
                        if (ss[1].indexOf("(") != -1) {
                            ss[1] = ss[1].substring(0, ss[1].indexOf("("));
                        }
                        bw.write("    /**" + ss[2] + "*/");
                        bw.newLine();
                        bw.write("    private " + GZ.get(ss[1]) + " " + ss[0] + ";");
                        bw.newLine();
                        list.add(GZ.get(ss[1]) + ":" + ss[0]);
                    }
                }
            }
            if (bw != null && domainName != null) {
                // 上一次的bw, javafile, domainName
                buff.add(domain);
            }
            //处理所有的 bw
            for (Domain po:buff) {
                for (String str:po.list) {
                    String ss2[] = str.split(":");
                    String name = ss2[1].substring(0, 1).toUpperCase() + ss2[1].substring(1);
                    po.bw.write("    public void set" + name + "(" + ss2[0] + " " + ss2[1] + "){");
                    po.bw.newLine();
                    po.bw.write("        this." + ss2[1] + " = " + ss2[1]+ ";");po.bw.newLine();
                    po.bw.write("    }");po.bw.newLine();
                    po.bw.write("    public " + ss2[0] + " get" + name + "(){");po.bw.newLine();
                    po.bw.write("        return this." + ss2[1] + ";");po.bw.newLine();
                    po.bw.write("    }");po.bw.newLine();
                }
                po.bw.write("}");
                po.bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                try {
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    private static Map<String, String> GZ = new HashMap<>();
    static {
        GZ.put("int", "Integer");GZ.put("bigint", "Integer");GZ.put("text", "String");
        GZ.put("varchar", "String");GZ.put("char", "String");GZ.put("datetime", "java.util.Date");
        GZ.put("date", "java.util.Date");
        GZ.put("float", "float");
        GZ.put("double", "double");
        GZ.put("decimal", "java.math.BigDecimal");
    }
    private static class Domain{
        File dest;
        List<String> list;
        BufferedWriter bw;
    }
}
