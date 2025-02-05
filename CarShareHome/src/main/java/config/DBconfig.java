package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBconfig {
	public Map<String, String> getDBinfo() throws FileNotFoundException{
		String db_properties_file="Z\\code_new\\grp_a_01\\CarShareHome\\DBconfig.properties";
		Properties db_info=new Properties();
		FileInputStream db_file_stream= new FileInputStream(db_properties_file);
		try {
			db_info.load(db_file_stream);
		} catch (IOException e) {
			System.out.println("データベース設定ファイルが認識できませんでした");
			e.printStackTrace();
		}
		String db_url= db_info.getProperty("url");
		String db_user =db_info.getProperty("user");
		String db_pass= db_info.getProperty("password");
		
		Map<String, String> getDBinfoForMap = new HashMap<>();
		
		getDBinfoForMap.put("url", db_url);
		getDBinfoForMap.put("user", db_user);
		getDBinfoForMap.put("password", db_pass);
		
		return getDBinfoForMap;
	}
}
