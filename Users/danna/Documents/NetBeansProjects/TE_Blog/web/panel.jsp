<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.modelo.blog"%>
<% 
     if (session.getAttribute("logueado")!="OK"){
         response.sendRedirect("login.jsp");
     }

    List<blog> lista =(List<blog>) request.getAttribute("lista");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>El Mundo de la Musica</h1>
        <p>Usuario:${sessionScope.usuario} </p>
        <br>
        <a href="LoginControlador?action=logout">Salir</a>
        <h2>El Blog de Imaginativo </h2>
        <p>
        <a href="MainController?op=nuevo">Nueva Entrada</a>
        </p>
       
        <form>
            
            <p>ID:${item.id}</p>
            <p>Fecha:${item.fecha}</p>    
            <p>Titulo:${item.titulo}</p>
            <p>Descripcion:${item.descripcion}</p>
            <p>Autor:${item.autor}</p>
           <a href="MainController?op=editar&id=${item.id})">Editar</a>
           <a href="MainController?op=eliminar&id=${item.id}"onclick="return(confirm('Esta seguro de eliminar'))">Editar</a>
           
        </form> 
        
    </body>
</html>
