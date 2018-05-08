package com.myfinal.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class SqlFilter
 */
public class SqlFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SqlFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		Throwable problem=null;
		try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String paramValue = "";
            java.util.Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String param = (String) params.nextElement().toString();
                String[] values = servletRequest.getParameterValues(param);
                for (int i = 0; i < values.length; i++) {
                    paramValue = paramValue + values[i];
                }
            }
            if (sqlValidate(paramValue)) {
                throw new Exception("The request you send contains illegal characters.");

            } else {
                chain.doFilter(request, response);
            }

        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }
		 if (problem != null) {
	            if (problem instanceof ServletException) {
	                throw (ServletException) problem;
	            }
	            if (problem instanceof IOException) {
	                throw (IOException) problem;
	            }
	            sendProcessingError(problem, response);
	        }
	}
	
	protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
                + "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
                + "table|from|grant|use|group_concat|column_name|"
                + "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
                + "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加  
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;

    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
	
	 private void sendProcessingError(Throwable t, ServletResponse response) {
	        String stackTrace = getStackTrace(t);

	        if (stackTrace != null && !stackTrace.equals("")) {
	            try {
	                response.setContentType("text/html");
	                PrintStream ps = new PrintStream(response.getOutputStream());
	                PrintWriter pw = new PrintWriter(ps);
	                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

	                pw.print("<h1>Error</h1>\n<pre>\n");
	                pw.print(stackTrace);
	                pw.print("</pre></body>\n</html>"); 
	                pw.close();
	                ps.close();
	                response.getOutputStream().close();
	            } catch (Exception ex) {
	            }
	        } else {
	            try {
	                PrintStream ps = new PrintStream(response.getOutputStream());
	                t.printStackTrace(ps);
	                ps.close();
	                response.getOutputStream().close();
	            } catch (Exception ex) {
	            }
	        }
	    }

}
