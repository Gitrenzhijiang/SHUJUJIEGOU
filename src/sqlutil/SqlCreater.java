package sqlutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 根据指定的文件夹, 把下面的所有.java文件
 * 如果这个文件是PO对象
 * @author renzhijiang
 *
 */
public class SqlCreater {

    public static void main(String[] args) throws IOException {
        File dest = new File("D://createSQL.sql");
        File dir = new File("C:\\Users\\Administrator\\Desktop\\common");
        new SqlCreater().createSQL(dest, dir);
    }
    /**
     * 将dir下面所有.java文件(po)-->sql文件
     * @param dest
     * @param dir
     * @throws IOException 
     */
    public void createSQL(File dest, File dir) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("错误的文件夹:" + dir.getAbsolutePath());
        }
        if (!dest.exists()) {
            dest.createNewFile();
        }
        File[] files = dir.listFiles(new FileFilter() {
            
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".java")) {
                    return true;
                }
                return false;
            }
        });
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest, true), "UTF-8"));
            for (File domain : files) {
                createSQL0(bw, domain);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if (bw != null)
                        bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
        
    }
    private void createSQL0(BufferedWriter bw, File javaFile) throws IOException {
        BufferedReader br = null;
        List<String> templist = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(javaFile), "UTF-8"));
            String line = null;
            //正则匹配
            Pattern p = Pattern.compile(" *\\bprivate\\b +\\b[a-zA-Z0-9.]+\\b +\\b[a-zA-Z0-9._]+\\b; *");
            while ((line = br.readLine()) != null) {
                if (p.matcher(line.trim()).matches()) {
                    
                    String[] strs = line.trim().split(" +");
                    if (strs.length == 3) {
                        strs[2] = strs[2].substring(0, strs[2].length() - 1);
                        templist.add(strs[0]+":"+strs[1]+":"+strs[2]);
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        
        bw.newLine();
        String tableName = javaFile.getName().substring(0, javaFile.getName().lastIndexOf(".")).toLowerCase();
        bw.write("# " + tableName);
        bw.newLine();
        bw.write("create table `" + tableName + "`(");
        bw.newLine();
        System.out.println(templist.size());
        for (int i = 0;i < templist.size();i++) {
            String[] sps = templist.get(i).split(":");
            String last = ",";
            if (i == templist.size()-1) {
                last = "";
            }
            bw.write(TAB + "`" + sps[2] + "`" + " " + GZ.get(sps[1]) + last);
            bw.newLine();
        }
        bw.write(");");
        bw.flush();
    }
    private static String TAB = "    ";
    private static Map<String, String> GZ = new HashMap<>();
    /*
     * int --> int Integer--> int
     * String--> varchar(255)
     * char  --> char(1) Character
     * Date --> datetime
     * float --> float Float --> float
     * double | Double --> double
     * BigDecimal decimal(10,2)
     */
    static {
        GZ.put("int", "int");GZ.put("Integer", "int");
        GZ.put("String", "varchar(255)");GZ.put("char", "char(1)");GZ.put("Character", "char(1)");
        GZ.put("Date", "datetime");GZ.put("java.util.Date", "datetime");
        GZ.put("float", "float");GZ.put("Float", "float");
        GZ.put("double", "double");GZ.put("Double", "double");
        GZ.put("BigDecimal", "decimal(10,2)");
    }
}
