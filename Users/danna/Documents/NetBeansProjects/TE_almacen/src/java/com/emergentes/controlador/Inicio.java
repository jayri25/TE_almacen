
package com.emergentes.controlador;

import com.emergentes.dao.ProductoDAO;
import com.emergentes.dao.ProductoDAOimplementacion;
import com.emergentes.modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
              ProductoDAO dao = new ProductoDAOimplementacion();
              //recibe el id
              int id;
              // gestiona registro
              Producto pro = new Producto();
              
            String action = (request.getParameter("action")!= null) ? request.getParameter("action"): "view";
            switch(action){
                case "add":
                    //Nuevo registro
                    request.setAttribute("producto", pro);
                    request.getRequestDispatcher("frmproducto.jsp").forward(request, response);
                    break;
                case "edit":
                    //edita un registro
                    id= Integer.parseInt(request.getParameter("id"));
                    pro = dao.getById(id);
                    request.setAttribute("producto", pro);
                    request.getRequestDispatcher("frmproducto.jsp").forward(request, response);
                    break;
                case "delete":
                    //elimina un registro
                    id=Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    request.getRequestDispatcher("Inicio").forward(request, response);
                    break;
                default:
                    //listar los registros
                    List<Producto>lista=dao.getAll();
                    request.setAttribute("productos", lista);
                    request.getRequestDispatcher("listado.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
             System.out.println("Error" + e.getMessage());
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAOimplementacion();
        int id =Integer.parseInt(request.getParameter("id"));
        String descripcion =request.getParameter("descripcion");
        int stock =Integer.parseInt(request.getParameter("stock"));
        
        Producto pro = new Producto();
        
        pro.setId(id);
        pro.setDescripcion(descripcion);
        pro.setStock(stock);
        
        if (id == 0){
        //nuevo registro
            try {
                dao.insert(pro);
                response.sendRedirect("Inicio");
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        }else{
        //actualizacion de un registro
        
             try {
                dao.update(pro);
                response.sendRedirect("Inicio");
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        
        }
    }

   
   

}
