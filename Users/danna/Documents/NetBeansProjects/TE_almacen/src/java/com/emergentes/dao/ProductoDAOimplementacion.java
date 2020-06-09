
package com.emergentes.dao;

import com.emergentes.modelo.Producto;
import com.emergentes.utiles.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAOimplementacion extends ConexionBD implements ProductoDAO {

    @Override
    public void insert(Producto producto) throws Exception {
        try {
            this.conectar();
            String sql ="INSERT into productos(descripcion, stock) values (?,?)";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, producto.getDescripcion());
            ps.setInt(2, producto.getStock() );
            
            //se ejecuta la consulta
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
    }finally{
            this.deconectar();
    }
        
    }

    @Override
    public void update(Producto producto) throws Exception {
        
        try {
            this.conectar();
            String sql ="UPDATE productos set descripcion=?, stock=? where id=?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, producto.getDescripcion());
            ps.setInt(2, producto.getStock());
            ps.setInt(3, producto.getId());
            
            //se ejecuta la consulta
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
    }finally{
            this.deconectar();
    }
        
}

    @Override
    public void delete(int id) throws Exception {
        
             try {
            this.conectar();
            String sql ="DELETE from productos where id= ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            //se ejecuta la consulta
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
    }finally{
            this.deconectar();
    }
    }

    @Override
    public Producto getById(int id) throws Exception {
        Producto pro = new Producto();
        try {
            this.conectar();
            String Sql = "select * from productos where id = ?";
            PreparedStatement ps = this.conn.prepareStatement(Sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            pro.setId(rs.getInt("id"));
            pro.setDescripcion(rs.getString("descripcion"));
            pro.setStock(rs.getInt("stock"));
           
            
            }
        } catch (Exception e) {
            throw e;
        }finally{
        this.deconectar();
        }
        return pro;
    }

    @Override
    public List<Producto> getAll() throws Exception {
        
        List<Producto> lista =null;
        try {
            this.conectar();
            String sql ="select * from productos";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            lista = new ArrayList<Producto>();
            while(rs.next()){
                Producto pro = new Producto();
                 pro.setId(rs.getInt("id"));
                 pro.setDescripcion(rs.getString("descripcion"));
                 pro.setStock(rs.getInt("stock"));
                 
                 //Se adiciona a la coleccion
                 lista.add(pro);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            this.deconectar();
        }
            return lista;
        
    }
}