package mok.localization.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mok.localization.utils.Classifier;

public class MainServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781822939400095723L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//      resp.setContentType("text/plain");
		//      resp.getWriter().println("Hello, this is a testing servlet. \n\n");
		// Properties p = System.getProperties();
		// p.list(resp.getWriter());
		try{
			String ask=req.getParameter("bssids");
//			System.out.println("ask is : "+ask);
			InputStream prop=getServletContext().getResourceAsStream("/resources/cfg.properties");
			Properties p=new Properties();
			p.load(prop);
			Classifier c=new Classifier(p);
			String response=c.eval(ask,getServletContext());
			System.out.println(response);
			String correctOne=req.getParameter("correct");
			resp.setContentType("text/plain");
			if(correctOne!=null && !correctOne.equals("")){
				resp.getWriter().println(response+" the correct is:"+correctOne+"\n\n");
			}else
				resp.getWriter().println(response+"\n\n");
		}catch(Exception e){
			resp.setContentType("text/plain");
			resp.getWriter().println("Error in classifying \n\n");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		try{
//			String ask=req.getParameter("bssids");
////			System.out.println("ask is : "+ask);
//			InputStream is=getServletContext().getResourceAsStream("/resources/nb.model");
//			InputStream isHead=getServletContext().getResourceAsStream("/resources/head");
//			Classifier c=new Classifier(p);
//			String response=c.eval(ask,is,isHead);
//			System.out.println(response);
//			resp.setContentType("text/plain");
//			resp.getWriter().println(response+"\n\n");
//		}catch(Exception e){
//			resp.setContentType("text/plain");
//			resp.getWriter().println("Error in classifying \n\n");
//		}
	}
//	public static void main(String[] args) {
//		MainServlet ms=new MainServlet();
//		ms.f();
//	}
//
//	private void f() {
//		try{
//			 String ask="00:1f:45:4b:9e:29,-39,00:1f:45:4b:9e:28,-40,00:1f:45:4b:98:e8,-50,00:1f:45:4b:98:e9,-56,00:1f:45:4b:9e:20,-57,00:1f:45:4b:9e:21,-58,00:1f:45:4b:a1:98,-59,00:1f:45:4b:a1:99,-60,00:1f:45:4b:98:e0,-65,00:1f:45:4b:94:28,-66,00:1a:70:31:ab:30,-69,00:1f:45:4b:a1:91,-71,00:1f:45:4b:a1:90,-72,00:1f:45:4b:a0:f9,-77,?";
////			System.out.println("ask is : "+ask);
////			InputStream is=getServletContext().getResourceAsStream("/WEB-INF/resources/nb.model");
////			InputStream isHead=getServletContext().getResourceAsStream("/WEB-INF/resources/head");
//			InputStream is=new FileInputStream("./resources/nb.model");
//			InputStream isHead=new FileInputStream("./resources/head");
//			 Classifier c=new Classifier();
//			String response=c.eval(ask,is,isHead);
//			System.out.println(response);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
}
