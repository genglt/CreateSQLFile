package util.sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateSQLFile {

	/**
	 * 批量生成sql语句
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "C:\\Users\\lchdev391\\Desktop\\genglt\\test\\sql.csv";
		String outPath = "C:\\Users\\lchdev391\\Desktop\\genglt\\test\\organ_cache.sql";
		outIntoFile(insertInto(getStr(path)),outPath);
		
		
	}
	
	public static List<String> getStr(String path){
		BufferedReader br = null;
		List<String> str = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(path));
			while(br.ready()){
				str.add(br.readLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}
	
	public static List<String> insertInto(List<String> str){
		List<String> sqls = new ArrayList<String>();
		for(int i=0 ; i<str.size() ; i++){
			String[] sqlParam = str.get(i).split(",");
			System.out.println(sqlParam[0]+"--"+sqlParam[1]);
			String sql = "insert into cms_organ_cache(organ_no,create_user,create_time,node_id,node_name,node_ip,http_port,node_socket_port)"+
			"(select '"+sqlParam[0]+"','admin',to_char(sysdate, 'yyyy/mm/dd hh24:mi'),csg.group_id,csg.group_name,csi.server_ip,csi.http_port,csi.socket_port"+
			"from content_server_group csg left join content_server_info csi on csg.group_id = csi.group_idwhere csg.group_id = '"+sqlParam[1]+"');";
			sqls.add(sql);
			
			
		}
		return sqls;
	}
	
	public static void outIntoFile(List<String> sqls,String outPath){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outPath));
			for(int i=0 ; i<sqls.size() ; i++){
				String sql = sqls.get(i);
				bw.write(sql);
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
