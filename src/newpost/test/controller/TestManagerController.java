package newpost.test.controller;

import javafx.geometry.Pos;
import newpost.controller.ManagerController;
import newpost.controller.interfaces.IManagerController;
import newpost.db.AppDataContainer;
import newpost.db.InitDB;
import newpost.model.common.Address;
import newpost.model.common.Passport;
import newpost.model.common.Product;
import newpost.model.common.Size;
import newpost.model.office.Client;
import newpost.model.office.PostTicket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 08.07.2016.
 */
public class TestManagerController {

    public static void main(String[] args) {
        AppDataContainer appDataContainer = new AppDataContainer();

        InitDB.initDB(appDataContainer);

        IManagerController managerController = new ManagerController(appDataContainer);

        testAddClient((ManagerController) managerController);

        testGetClient((ManagerController) managerController);

        testCreateTicket((ManagerController) managerController);

        testShowTicketByClientPhone((ManagerController) managerController);

        testSortTicketsByAddress((ManagerController) managerController);

        testSortClientByName((ManagerController) managerController);

        testSortTicketsByPrice((ManagerController) managerController);

        testSortTicketsById((ManagerController) managerController);

    }

    public static void testAddClient(ManagerController managerController) {

        Passport in1 = new Passport("ER546454", "Bob Jason");
        String in2 = "0935551666";
        Client expected = new Client("0935551666", new Passport("ER546454", "Bob Jason"));

        Client actual = managerController.addClient(in1, in2);

        System.out.printf("%s, test add client in1 %s,\nexpected %s,\nactual %s\n",
                actual.toString().equals(expected.toString()), in1, expected, actual);
        System.out.println();
    }

    private static void testGetClient(ManagerController managerController) {

        managerController.addClient(new Passport("ER546454", "Bob Jason"), "0935551666");

        String in = "0935551666";
        Client expected = new Client("0935551666", new Passport("ER546454", "Bob Jason"));
        Client actual = managerController.getClient(in);
        System.out.printf("%s, test get client in %s,\nexpected %s,\nactual %s\n",
                actual.toString().equals(expected.toString()), in, expected, actual);
        System.out.println();
    }

    private static void testCreateTicket(ManagerController managerController) {

        System.out.println("Testing CreateTicket method:");

        Passport in1 = new Passport("Bob Jason", "ER546454");
        String in2 = "0935551666";
        Client expected = new Client("0935551666", new Passport("ER546454", "Bob Jason"));

        Address address = new Address("Kiyv", "Lesi", "2");

        Product product1 = new Product("ProductName1", new Size(1, 1, 1, 1), 1000, expected);
        Product product2 = new Product("ProductName2", new Size(1, 1, 1, 1), 1000, expected);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        PostTicket postTicket = managerController.createTicket(expected, address, productList);

        if (!in1.getFullname().equals(postTicket.getClient().getPassport().getFullname())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        if (!in1.getNumber().equals(postTicket.getClient().getPassport().getNumber())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        if (!expected.getPhone().equals(postTicket.getClient().getPhone())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        if (!address.getCity().equals(postTicket.getTo().getCity())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        if (!address.getStreet().equals(postTicket.getTo().getStreet())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        if (!address.getHouseNum().equals(postTicket.getTo().getHouseNum())) {
            System.out.println("Not passed");
            System.out.println();
            return;
        }
        for (Product p : productList) {
            if (!p.getName().equals(postTicket.getProducts()[0].getName())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (p.getPrice() != (postTicket.getProducts()[0].getPrice())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
        }

        System.out.println("Passed");
        System.out.println();
    }

    private static void testShowTicketByClientPhone(ManagerController managerController) {
        System.out.println("Testing ShowTicketByClientPhone method:");

        Passport in1 = new Passport("ER546454", "Bob Jason");
        String in2 = "0935551666";
        Client client = new Client("0935551666", new Passport("ER546454", "Bob Jason"));

        Address address = new Address("Kiyv", "Lesi", "2");

        Product product1 = new Product("ProductName1", new Size(1, 1, 1, 1), 1000, client);
        Product product2 = new Product("ProductName2", new Size(1, 1, 1, 1), 1000, client);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        PostTicket postTicketExpected = managerController.createTicket(client, address, productList);

        List<PostTicket> postTicketActual = managerController.showTicketByClientPhone(in2);

        for(PostTicket p : postTicketActual) {
            if (!in1.getFullname().equals(p.getClient().getPassport().getFullname())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (!in1.getNumber().equals(p.getClient().getPassport().getNumber())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (!in2.equals(p.getClient().getPhone())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (!address.getCity().equals(p.getTo().getCity())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (!address.getStreet().equals(p.getTo().getStreet())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (!address.getHouseNum().equals(p.getTo().getHouseNum())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }

        }
        /*
        for (Product p : productList) {
            if (!p.getName().equals(p.getProducts()[0].getName())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
            if (p.getPrice() != (postTicketActual.getProducts()[0].getPrice())) {
                System.out.println("Not passed");
                System.out.println();
                return;
            }
        }*/

        System.out.println("Passed");
        System.out.println();
    }

    private static void testSortTicketsByAddress(ManagerController managerController) {
        List<PostTicket> post = managerController.sortTicketsByAddress();
        for (PostTicket ticket : post) {
            System.out.println(ticket.getFrom().toString());
        }
    }

    private static void testSortClientByName(ManagerController managerController) {
        List<Client> clients = managerController.sortClientsByName();
        for (Client client : clients) {
            System.out.println(client.toString());
        }
    }

    private static void testSortTicketsByPrice(ManagerController managerController) {
        List<PostTicket> tickets = managerController.sortTicketsByPrice();
        for (PostTicket ticket : tickets) {
            System.out.println(ticket.getPrice());
        }
    }

    private static void testSortTicketsById(ManagerController managerController) {
        List<PostTicket> tickets = managerController.sortTicketsById();
        for (PostTicket ticket : tickets) {
            System.out.println(ticket.getId());
        }
    }
}

