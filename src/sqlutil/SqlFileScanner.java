package sqlutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqlFileScanner {
	
	public static void main(String[] args)  {
		String filePath = "D:\\myfile\\项目\\暑假项目\\需求分析\\database\\testname.sql";
		File file = new File(filePath);
		if(file == null || !file.exists())
		{
			System.out.println("文件不存在");
			return;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.startsWith("CREATE TABLE")) {
					//CREATE TABLE `backup` (
					line = line.replace("CREATE TABLE `", "").replace("` (", "");
					System.out.println("==="+line+"===");
					
				}else if(line.startsWith("  `")) {
					//  `description` varchar(100) NOT NULL COMMENT '描述备份文件的信息',
					line = line.replace("  `", "");
					String fieldName = line.substring(0, line.indexOf("`"));
					System.out.print(fieldName + "\t");
					line = line.replace(fieldName + "` ", "");
					String typeName = line.substring(0, line.indexOf(" "));
					System.out.print(typeName + "\t");
					if(line.contains("COMMENT '")) {
						System.out.println(line.substring(line.indexOf("'")+1, line.indexOf("',")));
					}else {
						System.out.println(" ");
					}
					
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if(reader != null)
						reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	

}
