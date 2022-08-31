package ru.clevertec.checkrunner.servlets;


import org.springframework.web.WebApplicationInitializer;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


import ru.clevertec.checkrunner.config.AppConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfiguration.class);
        context.refresh();


        CardServlet cardServlet = (CardServlet) context.getBean("cardServlet");
        GetAllCardsServlet getAllCardsServlet = (GetAllCardsServlet) context.getBean("getAllCardsServlet");
        GetAllItemsServlet getAllItemsServlet = (GetAllItemsServlet) context.getBean("getAllItemsServlet");
        ItemServlet itemServlet = (ItemServlet) context.getBean("itemServlet");
        ReceiptServlet receiptServlet = (ReceiptServlet) context.getBean("receiptServlet");



        ServletRegistration.Dynamic card= servletContext.addServlet("cardServlet",cardServlet);
        ServletRegistration.Dynamic cards= servletContext.addServlet("getAllCardsServlet",getAllCardsServlet);
        ServletRegistration.Dynamic items= servletContext.addServlet("getAllItemsServlet", getAllItemsServlet);
        ServletRegistration.Dynamic item= servletContext.addServlet("itemServlet",itemServlet);
        ServletRegistration.Dynamic receipt= servletContext.addServlet("receiptServlet",receiptServlet);


        cards.addMapping("/api/cards");
        card.addMapping("/api/card/");
        item.addMapping("/api/item/");
        items.addMapping("/api/items");
        receipt.addMapping("/api/receipt");
    }
}
