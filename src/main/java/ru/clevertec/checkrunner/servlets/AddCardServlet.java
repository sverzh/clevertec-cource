package ru.clevertec.checkrunner.servlets;

import com.google.gson.Gson;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.CardSqlStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/card")
public class AddCardServlet extends HttpServlet {
    final CardSqlStorage cardSqlStorage = new CardSqlStorage();

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
