package com.xh.sdk.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {  
    @SuppressWarnings("unchecked")  
    // 创建excel文件函数  
    // src为待保存的文件路径,json为待保存的json数据  
    public static JSONObject createExcel(String src, JSONObject json) {  
        JSONObject result = new JSONObject(); // 用来反馈函数调用结果
        try {  
            // 新建文件  
            File file = new File(src);  
            file.createNewFile();  
  
  
            OutputStream outputStream = new FileOutputStream(file);// 创建工作薄  
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(outputStream);  
            WritableSheet sheet = writableWorkbook.createSheet("First sheet", 0);// 创建新的一页  
  
            JSONArray jsonArray = json.getJSONArray("data");// 得到data对应的JSONArray  
            Label label; // 单元格对象  
            int column = 0; // 列数计数  
  
            // 将第一行信息加到页中。如：姓名、年龄、性别  
            JSONObject first = jsonArray.getJSONObject(0);  
            
            for (Map.Entry<String, Object> entry : first.entrySet()) {
               // System.out.println(entry.getKey() + ":" + entry.getValue());
                label = new Label(column++, 0,  entry.getKey()+""); // 第一个参数是单元格所在列,第二个参数是单元格所在行,第三个参数是值  
                sheet.addCell(label); // 将单元格加到页  
            }
            
            
   
  
            // 遍历jsonArray  
            for (int i = 0; i < jsonArray.size(); i++) {  
                JSONObject item = jsonArray.getJSONObject(i); // 得到数组的每项  
                
 
                column = 0;// 从第0列开始放  
                
                
                for (Map.Entry<String, Object> entry : item.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                	  label = new Label(column++, (i + 1), entry.getValue()+""); // 第一个参数是单元格所在列,第二个参数是单元格所在行,第三个参数是值  
                      sheet.addCell(label); // 将单元格加到页  
                }

            }  
            writableWorkbook.write(); // 加入到文件中  
            writableWorkbook.close(); // 关闭文件，释放资源  
        } catch (Exception e) {  
        	System.out.println( e.getMessage());
            result.put("result", "failed"); // 将调用该函数的结果返回  
            result.put("reason", e.getMessage()); // 将调用该函数失败的原因返回  
            return result;  
        }  
        result.put("result", "successed");  
        return result;  
    }  
  
} 