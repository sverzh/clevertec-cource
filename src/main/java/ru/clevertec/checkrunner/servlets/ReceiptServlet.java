package ru.clevertec.checkrunner.servlets;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checkrunner.model.Receipt;
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


@Component
@RequiredArgsConstructor
public class ReceiptServlet extends HttpServlet {
    private final PrintPdfService pdfService;
    private  Receipt receipt;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String params = req.getParameter("params");
        String[] args1 = params.split(" ");


        if (args1.length!=0){
        StringParserWithoutFileStorages stringParser = new StringParserWithoutFileStorages(args1);
        receipt = stringParser.getReceipt();
        pdfService.printReceiptToPdf(receipt);

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
