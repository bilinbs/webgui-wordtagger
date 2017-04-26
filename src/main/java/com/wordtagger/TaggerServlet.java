package com.wordtagger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
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
    private static final String UPLOAD_DIRECTORY = "";
       
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
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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
                        String name = new File(item.getName()).getName();
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
        System.out.println(output);
        request.getRequestDispatcher("/WEB-INF/tagOutput.jsp").forward(request,
                response);
    }

	private String tagText(String input, String sessionId) {
	    Path path;
	    String opFileName = "."+ File.separator + "output" + sessionId + ".txt";
	    StringBuffer output = new StringBuffer();
        try {
            path = Files.createTempFile(sessionId, ".txt");
            System.out.println(path.toString());
            Files.write(path, input.getBytes("utf-8"), StandardOpenOption.CREATE);
            Runtime.getRuntime().exec("java FileChanges  " + path.toString() + " " + opFileName);
            System.out.println(new File(".").getAbsolutePath());
            List<String> lines = Files.readAllLines(Paths.get(opFileName), Charset.forName("utf-8"));
            for(String line : lines){
                output.append(line).append("\n");
            }
            Files.deleteIfExists(Paths.get(opFileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
		return output.append(input).toString() ;
	}

}
