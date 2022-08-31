package ru.clevertec.checkrunner.servlets;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.repository.CardSqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CardServlet extends HttpServlet {

    final CardSqlStorage cardSqlStorage;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String s = new Gson().toJson(cardSqlStorage.get(Integer.parseInt(req.getParameter("cardnumber"))));
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(s);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cardSqlStorage.delete(cardSqlStorage.get(Integer.parseInt(req.getParameter("cardnumber"))).getNumber());
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Card source = new Gson().fromJson(req.getReader(), Card.class);
        cardSqlStorage.add(source);
        try (PrintWriter writer = resp.getWriter()){
            writer.write(source.toString());
            resp.setStatus(201);
        }
    }
}
