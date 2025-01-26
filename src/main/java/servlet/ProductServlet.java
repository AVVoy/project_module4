package servlet;

import entity.Product;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/secure/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();

        productService = (ProductService) servletContext.getAttribute("productService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        HttpSession session = req.getSession();
        UUID userId = (UUID) session.getAttribute("userId");
        List<Product> userProducts = products.stream()
                .filter(product -> product.getUserId().equals(userId))
                .toList();

        req.setAttribute("products", userProducts);

        req.getRequestDispatcher("/secure/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String imageUrl = req.getParameter("imageUrl");

        HttpSession session = req.getSession();

        productService.createProduct(Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .imageUrl(imageUrl)
                .userId((UUID) session.getAttribute("userId"))
                .build()
        );

        resp.sendRedirect("/secure/products");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID productId = UUID.fromString(req.getParameter("productId"));

        productService.deleteProductById((UUID) productId);

        resp.sendRedirect("/secure/products");
    }
}
