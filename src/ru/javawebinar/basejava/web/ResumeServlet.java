package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() {
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>" +
                " <head>" +
                "  <meta charset=\"utf-8\">\n" +
                "<link rel=\"stylesheet\" href=\"css/style.css\">" +
                "  <title>Тег table</title>\n" +
                //" <style>" +
                //   " table {  width: 50%; /* Ширина таблицы */ + " +
                //  " background: #A6C9E2; /* Цвет фона таблицы */" +
                //   " color: #2E6E9E; /* Цвет текста */" +
                //   " border-spacing: 3px; /* Расстояние между ячейками */}" +
                //   "td, th {  background:  #A6C9E2; /* Цвет фона ячеек */     padding: 5px; /* Поля вокруг текста */ }" +
                //   "</style>" +
                " </head>" +
                " <body>" +
                //"  <table border=\"1\">\n" +
                "  <table>\n" +
                "   <tr>" +
                "    <th>UUID </th>\n" +
                "    <th>Full Name</th>\n" +
                "   </tr>";


        htmlContent += uuid == null ?
                storage.getAllSorted()
                        .stream()
                        .map(this::insertRow)
                        .reduce("", (s1, s2) -> s1 + s2) : insertRow(storage.get(uuid));
        htmlContent +=  " </table>" +
                        " </body>" +
                        "</html>";
        PrintWriter out = response.getWriter();
        out.println(htmlContent);

    }

    private String insertRow(Resume r) {
        return "   <tr>" +
                "    <td>" + r.getUuid() + "</td>" +
                "    <td>" + r.getFullName() + "</td>" +
                "  </tr>";
    }
}
