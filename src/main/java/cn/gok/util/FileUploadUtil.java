package cn.gok.util;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * �����ļ��Ĺ����࣬�����ļ��ϴ�����
 */
public class FileUploadUtil {
	/**
	 * ��������Ĺ����ǻ�ȡǰ�˵����ݼ��ϣ����ļ������File�Ա��������
	 * @param request
	 * @param servletContext
	 * @return
	 */
	public static List<FileItem> getRequsetFileItems(
			HttpServletRequest request,ServletContext servletContext){
		
		//�ж�enctype�����Ƿ�Ϊmultipart/form-data
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if(isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			String str="javax.servelet.context.tempdir";
            File repository=(File) servletContext.getAttribute(str);
            factory.setRepository(repository);
            ServletFileUpload upload=new ServletFileUpload(factory);
            try {
                return upload.parseRequest(request);
            }catch (FileUploadException e) {
                e.printStackTrace();
                return null;
            }
		}
		return null;
	}
	
	/**
	 * �����ļ�
	 * @param fileItem    		Ҫ��������ļ�
	 * @param savePath      	ָ���ı���Ŀ¼·��
	 * @param fileName      	Ҫ������ļ���
	 * @return      			����ɹ�����true,���򷵻�false
	 */
	public static boolean saveFile(FileItem fileItem,String savePath,String fileName) {        
        File file = new File(savePath);
        if(!file.exists()) {
        	file.mkdirs();
        }
        File uploadFile	= new File(savePath+File.separator+fileName);
        try{
        	fileItem.write(uploadFile);
            return true;
        }catch(Exception e){
        	e.printStackTrace();
        }
        return false;
    }
	
	/**
	 *��������Ĺ����ǻ�ȡ��ǰʱ�����1970��ļ������
	 */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
    
    /**
     * ��������Ĺ����ǵõ��µ�ͼƬ����
     * @param fileSuffix  ͼƬ�ĺ�׺
     * @return
     */
     public static String getImgNewName(String fileSuffix) {
         Date date=new Date();
         int second=getSecondTimestamp(date);
         String fileName=String.valueOf(second)+"."+fileSuffix;
         return fileName;
     }
     
     
	   /**
	    * ��ȡ�ļ���׺���� ֻҪjpg����png��ʽ
	    * @param item
	    * @return  	�����jpg���򷵻�jpg
	    * 		        ���png���򷵻�png
	    * 		        ���򷵻�null
	    */
	 public static String getFileSuffix(FileItem item) {
	     String fileFullName=item.getName();
	     File fileInfo=new File(fileFullName);
	     String suffix = fileInfo.getName().substring(fileInfo.getName().lastIndexOf(".") + 1);
	     if(suffix.equals("jpg") ||suffix.equals("png") ) {
	         return suffix;
	     }
	     return null;        
	 }
	 
	 
	 
}
