<%-- 
    Document   : index
    Created on : Sep 2, 2009, 11:05:54 AM
    Author     : marcelo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bienvenido</title>
    </head>
    <body>
        <h3 align="center">Iniciar Sesión</h3>
        <form name="Iniciar" action="http://localhost:8084/JSPServlets/Controlador" method="POST">
            <table border="0" align="center">
                <tr>
                    <td>Usuario: </td>
                    <td><input type="text" name="user" value="" /></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="pass" value="" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Enviar" name="Enviar" />
                        <input type="hidden" name="seleccion" value="1" /></td>
                </tr>
            </table>

        </form>
    </body>
</html>
