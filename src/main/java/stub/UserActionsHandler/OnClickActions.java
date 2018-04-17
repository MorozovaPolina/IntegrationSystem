package stub.UserActionsHandler;


import stub.exceptions.IncorrectRequirementsystemMaping;
import stub.exceptions.NoSuchSystem;
import stub.helpers.RequirementsTypes;
import stub.systems.ExistingSystem;
import stub.transaction.Transaction;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

import static stub.helpers.GlobalValues.Systems;
import static stub.systems.ExistingSystem.getSystem;

public class OnClickActions extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Click!");
        PrintWriter out = response.getWriter();
        out.println("Got");
        response.sendRedirect("http://localhost:8888/index.jsp");
        ExistingSystem SystemA = null;
        try {
            SystemA = getSystem("SystemA");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemB = null;
        try {
            SystemB = getSystem("SystemB");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemC = null;
        try {
            SystemC = getSystem("SystemC");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        ExistingSystem SystemD = null;
        try {
            SystemD = getSystem("SystemD");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
        }
        Transaction transaction = new Transaction(RequirementsTypes.Routing);

        transaction.addStep(SystemA, SystemB, 2);
        transaction.addStep(SystemD, SystemB, 2);
        transaction.addStep(SystemC, SystemB, 2);
        transaction.addStep(SystemB, SystemA, 2);
        transaction.addStep(SystemA, SystemC, 2);
        transaction.addStep(SystemC, SystemA, 2);
        transaction.addStep(SystemA, SystemD, 2);
        transaction.addStep(SystemD, SystemA, 2);
        transaction.addStep(SystemB, SystemC, 2);
        transaction.addStep(SystemB, SystemD, 2);
        transaction.addStep(SystemD, SystemC, 2);
        transaction.addStep(SystemC, SystemD, 2);

        try {
            transaction.startTransaction();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IncorrectRequirementsystemMaping incorrectRequirementsystemMaping) {
            incorrectRequirementsystemMaping.printStackTrace();
            System.out.println("Проблемы с согласованием требований и возможностей системы");
        } catch (NoSuchSystem noSuchSystem) {
            noSuchSystem.printStackTrace();
            System.out.println("Нет такой системы");
        }

     //   response.sendRedirect("index.jsp");

    }
}
