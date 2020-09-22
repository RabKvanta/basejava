package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public ResumeServlet() {
        // Register JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        storage = Config.get().getStorage();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>" +
                " <head>" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title>Тег table</title>\n" +
                " <style>"+
                " table {  width: 50%; /* Ширина таблицы */ + " +
                " background: #A6C9E2; /* Цвет фона таблицы */"+
                " color: #2E6E9E; /* Цвет текста */"+
                " border-spacing: 3px; /* Расстояние между ячейками */}"+
                "td, th {      background:  #A6C9E2; /* Цвет фона ячеек */     padding: 5px; /* Поля вокруг текста */ }"+
                "</style>"+
                " </head>" +
                " <body>" +
                "  <table border=\"1\">\n" +
                "   <tr>" +
                "    <th>UUID </th>\n" +
                "    <th>Full Name</th>\n" +
                "   </tr>";



        if (uuid == null) {
            for (Resume r : storage.getAllSorted()) {
                htmlContent += "   <tr>" +
                        "    <td>" + r.getUuid() + "</td>" +
                        "    <td>" + r.getFullName() + "</td>" +
                        "  </tr>";
            }
        } else {
            Resume r = storage.get(uuid);
            htmlContent += "   <tr>" +
                    "    <td>" + r.getUuid() + "</td>" +
                    "    <td>" + r.getFullName() + "</td>" +
                    "  </tr>";
        }
        htmlContent += " </table>" +
                " </body>" +
                "</html>";
        PrintWriter out = response.getWriter();
        out.println(htmlContent);

    }
}
