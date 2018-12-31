package com.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashBoardServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String TASK_FLOW_ID =
        "faces/FilmStrip";
  //  private static final String cloudHostName = "https://144.21.82.205/OmniyatPrism";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = null;
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>DashBoard Servlet</title></head>");
        out.println("<body>");
                String url = request.getRequestURL().toString();
                String subUrl =url.substring(0, url.lastIndexOf("/"));
        String jwtToken = request.getParameter("jwt");
        String salesLeaseToken = request.getParameter("SalesLeaseToken");
        // System.out.println("suburl="+subUrl);
        if (jwtToken != null && salesLeaseToken != null) { // this is for other instances
            System.out.println("Enter into other instance server");
            path =
                subUrl + "/" + TASK_FLOW_ID + "?" + "jwt" + "=" + jwtToken + "&SalesLeaseToken=" +
                salesLeaseToken;
        } else { //local dev running
            path =
                subUrl+ "/" +TASK_FLOW_ID +
                "?jwt=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsIng1dCI6IlRBc0pMVXY0MFVuaWRJclVrRGFwRzhFXzlLOCJ9.eyJleHAiOjE0ODM1MzA3NzEsImlzcyI6Ind3dy5vcmFjbGUuY29tIiwicHJuIjoiYXBpIiwiaWF0IjoxNDgzNTE2MzcxfQ.ALvDilyGj-VQUmRQnUc7L1tz895bxjiSfPetczwqbUhMTmBIIoyJd9tKFQTnYPg8esUiiym8RnXRisFXcWmdmcoYAg3bbhqQ877KBDdXg6_CAk5h4OSHG8jgXhWFSJsE-&SalesLeaseToken=S";
//                +salesLeaseToken;          
        } 
        System.out.println("Path======"+path);
        response.sendRedirect(path);
        out.println("<p>Redirecting to Dashboard taskflow</p>");
        out.println("</body></html>");
        out.close();
    }
}
