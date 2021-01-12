<%@page import="java.util.List"%>
<%@page import="Traitement.TraitementXml"%>
<%@page import="Traitement.APIExampleUse"%>
<%@page import="com.oreilly.servlet.MultipartRequest" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">
<HTML >
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <body>
        <%

            MultipartRequest m = new MultipartRequest(request, "G:\\wwssservice\\exxxmeple\\src\\java\\Ressource");
            

        %> 
        
        
        <form action="Afficher" method="get" >

            <input type="submit" value="Afficher">

        </form>
    </body>

