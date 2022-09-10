package ru.clevertec.checkrunner.servlets;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.springdata.ItemStorageDataJpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class ItemServlet extends HttpServlet {
    final ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Item item = itemService.findById(Integer.parseInt(req.getParameter("id")));
        final String s = new Gson().toJson(item);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(s);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        itemService.delete(Integer.parseInt(req.getParameter("id")));
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item source = new Gson().fromJson(req.getReader(), Item.class);
        itemService.save(source);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(source.toString());
            resp.setStatus(201);
        }
    }

        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Item source = new Gson().fromJson(req.getReader(), Item.class);
            itemService.update(source);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(source.toString());
                resp.setStatus(201);
            }
    }
}
