package cqnu.qb;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServletDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  resp.sendError(404,"你成功了！");
        String name=req.getParameter("name");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().append("It's OK:"+name);
    }

    //test
    public void test(){
        int i=0;
        i+=10;
        System.out.println("123");
    }
}
