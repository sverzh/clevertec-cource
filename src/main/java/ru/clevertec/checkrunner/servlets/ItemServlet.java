package ru.clevertec.checkrunner.servlets;

import com.google.gson.Gson;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/item/")
public class ItemServlet extends HttpServlet {
    final ItemSqlStorage itemSqlStorage = new ItemSqlStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Item item = itemSqlStorage.get(Integer.parseInt(req.getParameter("id")));
        final String s = new Gson().toJson(item);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(s);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        itemSqlStorage.delete(Integer.parseInt(req.getParameter("id")));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item source = new Gson().fromJson(req.getReader(), Item.class);
        itemSqlStorage.add(source);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(source.toString());
            resp.setStatus(201);
        }
    }
}
