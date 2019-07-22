package cqnu.qb;

import javax.servlet.*;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("放行");
        //1.创建代理对象，增强getParameter方法
        ServletRequest proxy_req=(ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               //判断是否是getParameter方法
                if(method.getName().equals("getParameter")){
                    //增强返回值
                    String value = (String) method.invoke(req,args);
                    if(value!=null){
                        for (String str:list){
                            if(value.contains(str)){
                                System.out.println("走到这里");
                                value=value.replaceAll(str,"***");
                            }
                        }
                    }
                    return value;
                }
                return method.invoke(req,args);
            }
        });

        //2.放行
        chain.doFilter(proxy_req, resp);
    }
    private List<String> list = new ArrayList<String>();//敏感词汇List集合
    public void init(FilterConfig config) throws ServletException {
        try {
            //1.加载文件(获取文件真实路径)
            ServletContext servletContext = config.getServletContext();
            System.out.println("servletContext:"+servletContext);
            String realPath = servletContext.getRealPath("/WEB-INF/test.txt");
            System.out.println("realPath:"+realPath);
            //2.读取文件
            //BufferedReader br = new BufferedReader(new FileReader(realPath)); //原语句 不能读取UFT-8
            InputStreamReader isr=new InputStreamReader(new FileInputStream(realPath),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            //3.将文件每一行数据添加到List中
            String line =null;
            while ((line=br.readLine())!=null){
                list.add(line);
            }
            br.close();
            System.out.println(list);
        }catch (Exception e){
            e.printStackTrace();
        }

       /* String s1 = "哈批";
        list.add(s1);
        String s2 = "弱智";
        list.add(s2);*/

    }

}
