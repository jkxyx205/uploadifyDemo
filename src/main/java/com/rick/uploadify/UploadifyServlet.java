package com.rick.uploadify;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rick.Xu on 2016/01/05.
 */
public class UploadifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map map = new HashMap();
        request.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = request.getRealPath("/files");
        factory.setRepository(new File(path));
        factory.setSizeThreshold(1024*1024) ;
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item : list){
                if(!item.isFormField()){ //is file upload
                    String name = item.getName() ;
                    name = name.substring(name.lastIndexOf(File.separator)+1); //IE9 is full path

                    String fileSuffix  = name.substring(name.lastIndexOf(".")+1,name.length());
                    String oldName = name.replaceAll("." + fileSuffix,"");
                    String fileName = oldName;//DateUtils.getNowTime(DateUtils.DATE_All_KEY_STR);
                    String newName = fileName + "." + fileSuffix;
                    OutputStream out = new FileOutputStream(new File(path,newName));
                    InputStream in = item.getInputStream() ;
                    int length ;
                    byte [] buf = new byte[1024] ;
                    while( (length = in.read(buf) ) != -1){
                        out.write(buf, 0, length);
                    }
                    in.close();
                    out.close();
                    /**将上传处理后的数据返回**/
                    map.put("fileSuffix",fileSuffix);
                    map.put("fileName",oldName);
                    map.put("filePath",fileName);
                    break;
                } else { // is parameter
                    System.out.println(item.getString() + ".............");
                }
            }
        }catch (Exception e) {
            System.out.println("出错了：" + e.getMessage());
        }
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        String msg = JSONObject.toJSONString(map);
        out.print(msg);
        out.close();
    }
}
