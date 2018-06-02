package com.adee.mybatis3.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generator {
	public static void main(String[] args) throws Exception {
		//java -jar mybatis-generator-core-x.x.x.jar -configfile \temp\generatorConfig.xml -overwrite
		//generatorConfig.xml.template
		//cd E:/maven/mvn_rep/org/mybatis/generator/mybatis-generator-core/1.3.6
		//java -jar mybatis-generator-core-1.3.6.jar -configfile generatorConfig.xml -overwrite
		
		String path = initGeneratorXml();
		exec("java -jar E:/maven/mvn_rep/org/mybatis/generator/mybatis-generator-core/1.3.6/mybatis-generator-core-1.3.6.jar -configfile " + path + "  -overwrite");
		System.out.println("java -jar E:/maven/mvn_rep/org/mybatis/generator/mybatis-generator-core/1.3.6/mybatis-generator-core-1.3.6.jar -configfile " + path + "  -overwrite");
	}
	
	private static String initGeneratorXml() throws Exception{
		Properties props = new Properties();
		props.load(Generator.class.getClassLoader().getResourceAsStream("init.properties"));
		//1.读取template文件，得到字符串
		String templStr;
		StringBuilder sb = new StringBuilder();
		String tmp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Generator.class.getClassLoader().getResourceAsStream("generatorConfig.xml.template"), "UTF-8"));
		while((tmp = reader.readLine()) != null) {
			sb.append(tmp).append("\n");
		}
		templStr = sb.toString();
		//2.替换字符串关键字
		Set<String> set$ = new HashSet<String>();
		Pattern pattern = Pattern.compile("\\$\\{[^\\}]*\\}");
		Matcher m = pattern.matcher(templStr);
		while(m.find()) {
			String s = templStr.substring(m.start(), m.end());
			set$.add(s);
		}
		
		String ret = templStr;
		for(String key : set$) {
			//System.out.println(key + " : " + props.getProperty(key.substring(2, key.length()-1)).trim());
			ret = ret.replace(key, props.getProperty(key.substring(2, key.length()-1)).trim());
		}
		//System.out.println(ret);
		
		//3.生成generatorConfig.xml，并将字符串写入generatorConfig.xml
		File f = new File("generatorConfig.xml");
		if(f.exists()) {
			f.delete();
		}
		f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
		bw.write(ret);
		bw.flush();
		
		String path = f.getAbsolutePath();
		
		//关闭文件描述符
		bw.close();
		reader.close();
		
		return path;
	}
	
	private static String handleRegExp(String reg) {
		System.out.println(reg.replaceAll("\\$\\{", "\\$\\{").replaceAll("\\}", "\\}"));
		return reg.replaceAll("\\$\\{", "\\$\\{").replaceAll("\\}", "\\}");
	}
	
	private static void exec(String s) {
		try {
			Runtime.getRuntime().exec(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
