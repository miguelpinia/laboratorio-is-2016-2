package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Logic.ImageHelper;
import model.Imagen;

/**
 *
 * @author miguel
 */
public class Imagenes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.replace(request.getContextPath() + "/", "");
        uri = uri.replace("%20", " ");
        uri = uri.substring(uri.indexOf("/") + 1);
        ImageHelper helper = new ImageHelper();
        Imagen img = helper.getImagenPorNombre(uri);
        response.setContentType(img.getMimeType().getNombre());
        ServletOutputStream out = response.getOutputStream();
        out.write(img.getContenido());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet get method. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }// </editor-fold>

}
