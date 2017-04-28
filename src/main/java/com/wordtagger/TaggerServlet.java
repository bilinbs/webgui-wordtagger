package com.wordtagger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class TaggerServlet
 */
public class TaggerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaggerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String input = "";
        // process only if its multipart content
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        //String name = new File(item.getName()).getName();
                        //item.write(new File(
                                //UPLOAD_DIRECTORY + File.separator + name));
                        input = item.getString();
                    }
                }
                // File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message",
                        "File Upload Failed due to " + ex);
            }
        } else {
            input = request.getParameter("inputText");

        }
        input = new String(input.getBytes("iso-8859-1"), "utf-8");
        String sessionId = request.getSession().getId();
        String output = tagText(input,sessionId);
        
        
        request.setAttribute("output", output);
        request.getRequestDispatcher("/WEB-INF/jsp/tagOutput.jsp").forward(request,
                response);
    }

	private String tagText(String input, String sessionId) {
	    Path ipPath, opPath;
	    String ipFilename = "input" + sessionId ;
	    String opFileName = "output" + sessionId ;
	    StringBuffer output = new StringBuffer();
        try {
            ipPath = Files.createTempFile(ipFilename, ".txt");
            opPath = Files.createTempFile(opFileName, ".txt");
            System.out.println(new File(".").getAbsolutePath());
            Files.write(ipPath, input.getBytes("utf-8"), StandardOpenOption.CREATE);
            Process proc = Runtime.getRuntime().exec("java FileChanges  " + ipPath.toString() + " " + opPath.toString());
            if(proc.waitFor()!=0){
            	return "Error while processing file";
            }
            List<String> lines = Files.readAllLines(opPath, Charset.forName("utf-8"));
            for(String line : lines){
                output.append(line).append("\n");
            }
            Files.deleteIfExists(Paths.get(opFileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		return output.toString() ;
	}

}
