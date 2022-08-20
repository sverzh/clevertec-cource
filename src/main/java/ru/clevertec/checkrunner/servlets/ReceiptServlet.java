package ru.clevertec.checkrunner.servlets;

import ru.clevertec.checkrunner.service.PrintPdfService;
import ru.clevertec.checkrunner.util.StringParserWithoutFileStorages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@WebServlet("/api/receipt")
public class ReceiptServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String params = req.getParameter("params");
        String[] args1 = params.split(" ");

        if (args1.length!=0){
        StringParserWithoutFileStorages stringParser = new StringParserWithoutFileStorages(args1);
        PrintPdfService pdfService = new PrintPdfService(stringParser.getParsedMap(), stringParser.getCard());
        pdfService.printReceiptToPdf();

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "inline; filename=receipt.pdf");
            OutputStream out = resp.getOutputStream();
            try (FileInputStream in = new FileInputStream("D:\\project\\src\\main\\resources\\receipt.pdf")) {
                int content;
                while ((content = in.read()) != -1) {
                    out.write(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();

    }
        else resp.setStatus(404);
    }


}
