package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * 将路径下的所有.java文件的编码尽可能的转换为UTF-8格式
 * @author renzhijiang
 *
 */
public class CharacterChange {
    public static void main(String[] args) throws IOException {
        // we need a dir
        String dirPath = "D:\\myfile\\JavaSource\\w1\\SHUJUJIEGOU\\src\\util\\";
        
        File dir = new File(dirPath);
        
        new CharacterChange().change(dir);
    }

    public void change(File dir) {
        if (dir == null || !dir.isDirectory()) {
            System.out.println("文件不存在或者不是一个文件夹!");
            return;
        }
        List<File> javaFiles = new ArrayList<>();
        getJavaRecursion(dir, javaFiles);
      //创建临时文件
        File temp = new File(dir,  "//_abc_abc_rzj_temp892323.java");
        try {
            if (temp.exists()) {
                temp.delete();
                temp.createNewFile();
            }else {
                temp.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始处理
        System.out.println("总共源文件:" + javaFiles.size());
        for (File file:javaFiles) {
            if (!getCharset(file).equals("UTF-8")) {
                System.out.println(file.getAbsolutePath() + ":" + getCharset(file));
            }
            textFileChange(file, temp);
            
        }
        temp.delete();
    }

    private void textFileChange(File file, File temp) {

        FileInputStream fis = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        String charset = getCharset(file);
        // 如果是utf-8编码, 退出
        if (charset.equalsIgnoreCase("UTF-8")) {
            return;
        }
        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, charset);
            br = new BufferedReader(isr);
            // 初始化写入的流
            fos = new FileOutputStream(temp);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        copyto(temp, file);
    }

    // 将source文件的内容覆盖到dest文件中
    public static void copyto(File source, File dest) {
        if (source == null || dest == null) {
            throw new IllegalArgumentException("文件不存在");
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(dest);
            byte[] buff = new byte[2048];
            int len = -1;
            while ((len = fis.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据文件得到该文件中文本内容的编码
     * 
     * @param file
     *            要分析的文件
     **/
    public static String getCharset(File file) {
        String charset = "GBK"; // 默认编码
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                            // (0x80 -0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                        // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                // System.out.println(loc + " " + Integer.toHexString(read));
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return charset;
    }
    
    private static void getJavaRecursion(File dir, List<File> javaFiles) {
        if (dir == null || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                getJavaRecursion(file, javaFiles);
            }else if(file.getName().endsWith(".java")) {
                javaFiles.add(file);
            }
        }
    }
}
