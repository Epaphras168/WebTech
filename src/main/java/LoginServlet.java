import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// This annotation maps the URL "/login" from our HTML form to this class
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (pass.length() < 8) {
            out.println("<h3>Hello " + user + ", your password is weak. Try a strong one.</h3>");
        } else {
            out.println("<h3>Welcome " + user + "</h3>");
        }
    }
}