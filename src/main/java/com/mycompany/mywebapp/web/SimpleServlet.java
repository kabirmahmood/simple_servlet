package com.mycompany.mywebapp.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SimpleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

        String a = request.getParameter("a");
        String b = request.getParameter("b");
        Integer sum = Integer.parseInt(a) + Integer.parseInt(b);

        PrintWriter out = response.getWriter();
        out.println( "<h1>SimpleServlet v5.5 - The sum of " + a.toString() + " + " + b.toString() + " = " + sum + "</h2>" );
        out.flush();
        out.close();
    }
}
