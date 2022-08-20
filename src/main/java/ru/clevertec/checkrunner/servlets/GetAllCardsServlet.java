package ru.clevertec.checkrunner.servlets;

import com.google.gson.Gson;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.CardSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/cards")
public class GetAllCardsServlet extends HttpServlet {
    final CardSqlStorage cardSqlStorage = new CardSqlStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String size = req.getParameter("size");
        String page = req.getParameter("page");
        List<Card> cards = cardSqlStorage.findAll(size,page);
        String json = new Gson().toJson(cards);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(json);
            resp.setStatus(200);
        }
    }
}
