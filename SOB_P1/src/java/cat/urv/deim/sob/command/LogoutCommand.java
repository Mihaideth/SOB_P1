package cat.urv.deim.sob.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.io.IOException;
import javax.servlet.http.HttpSession;

 

public class LogoutCommand implements Command {
    
    
    @Override
    public void execute(
    HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {
        
        
        HttpSession sesion = request.getSession();
        sesion.setAttribute("user2", null);
        sesion.invalidate();
        
        
        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/actius.do").forward(request, response);
            
    }
}
