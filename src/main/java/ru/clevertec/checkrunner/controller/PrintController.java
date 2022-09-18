package ru.clevertec.checkrunner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checkrunner.model.Receipt;
import ru.clevertec.checkrunner.service.PrintPdfService;
import ru.clevertec.checkrunner.util.StringParserWithoutFileStorages;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping({"/api/"})
@RequiredArgsConstructor
public class PrintController {

    private final PrintPdfService pdfService;


    @GetMapping({"/receipt/{arguments}"})
    public void printPdf(@PathVariable String arguments, HttpServletResponse resp) throws IOException {
        String[] args1 = arguments.split(" ");
        if (args1.length != 0) {
            StringParserWithoutFileStorages stringParser = new StringParserWithoutFileStorages(args1);
            Receipt receipt = stringParser.getReceipt();
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

        } else resp.setStatus(404);

    }

}