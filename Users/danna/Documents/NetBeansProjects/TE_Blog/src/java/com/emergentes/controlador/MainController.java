
package com.emergentes.controlador;

import com.emergentes.modelo.blog;
import com.emergentes.utiles.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String op;
            op = (request.getParameter("op") !=null) ? request.getParameter("op") : "list";
            ArrayList<blog> lista = new ArrayList<blog>();
            ConexionBD canal = new ConexionBD();
            Connection conn = canal.conectar();
            
            PreparedStatement ps;
            ResultSet rs;
            
            if(op.equals("list")){
            try{
              String sql="select * from blog";
              ps = conn.prepareStatement(sql);
              rs = ps.executeQuery();
              while (rs.next()){
                  blog blo = new blog();
                  blo.setId(rs.getInt("id"));
                  blo.setFecha(rs.getDate("fecha"));
                  blo.setTitulo(rs.getString("titulo"));
                  blo.setDescripcion(rs.getString("descripcion"));
                  blo.setAutor(rs.getString("autor"));
                  
                  lista.add(blo);
              }
              request.setAttribute("lista", lista);
              request.getRequestDispatcher("panel.jsp").forward(request, response);
            }catch(SQLException ex){
                  System.out.println("Error en SQL" + ex.getMessage());
            }finally {
            canal.desconectar();
            }
            }
            if(op.equals("nuevo")){
               blog b = new blog();
               request.setAttribute("blog", b);
               request.getRequestDispatcher("editar.jsp").forward(request, response);
                       
            }
            if(op.equals("eliminar")){
                try {
                    int id=Integer.parseInt(request.getParameter(("id")));
                    
                    String sql="delete from libros where id=?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, id);
                    
                    ps.executeUpdate();
                } catch (SQLException ex) {
                        System.out.println("Error de sql "+ex.getMessage());
                }finally{
                canal.desconectar();
                }
                response.sendRedirect("MainController");
            }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String autor = request.getParameter("autor");
        
        
       blog b = new blog();
       b.setId(id);
       b.setFecha(fecha);
       b.setTitulo(titulo);
       b.setDescripcion(descripcion);
       b.setAutor(autor);
       
       ConexionBD canal = new ConexionBD();
       Connection conn = canal.conectar();
       PreparedStatement ps;
       ResultSet rs;
       
       if(id == 0){
            try {
                String sql = "insert into blog(fecha,titulo,descripcion,autor) values (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setDate(1, (java.sql.Date) b.getFecha());
                ps.setString(2, b.getTitulo());
                ps.setString(3, b.getDescripcion());
                ps.setString(4, b.getAutor());
                
                ps.executeUpdate();
                
            } catch (SQLException ex) {
                     System.out.println("Error de SQL "+ex.getMessage());
            }finally{
            canal.desconectar();
            }
            response.sendRedirect("MainController");
       
       }
    }

 
  

}
