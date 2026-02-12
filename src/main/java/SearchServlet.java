import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fetch")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

//        System.out.println("You have come to the search page!");

        String query = request.getParameter("searchQuery");
        if (query != null && !query.trim().isEmpty()) {

            String googleUrl = "https://www.google.com/search?q=" + query;
            response.sendRedirect(googleUrl);
        } else {
            response.sendRedirect("search.html");
        }
    }
}