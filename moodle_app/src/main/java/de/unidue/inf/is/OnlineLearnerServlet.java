package de.unidue.inf.is;

import java.io.IOException;
import de.unidue.inf.is.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.utils.DBUtil;
import de.unidue.inf.is.stores.UserStore;

public final class OnlineLearnerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String UserName = "Ahmet Aker";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
  
        boolean databaseExists = DBUtil.checkDatabaseExistsExternal();

        if (databaseExists) {
        	UserStore myUser = new UserStore();
        	User answer = myUser.showUser(UserName);
            request.setAttribute("db2exists", "vorhanden! Supi!");
            request.setAttribute("Myanswer", answer);
            myUser.close();
        }
        else {
            request.setAttribute("db2exists", "nicht vorhanden :-(");
        }

        request.getRequestDispatcher("onlineLearner_start.ftl").forward(request, response);
    }

}