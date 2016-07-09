package newpost.controller;

import newpost.model.Address;
import newpost.model.Client;
import newpost.model.PostTicket;
import newpost.model.Product;

/**
 * Created by macaque on 09.07.2016.
 */
public interface IClientController {
    PostTicket makeOrder(Client client, Address sendToAdress, Product product);
    Product showProductById(int productId);
    boolean cancelTicket(int ticketId);
    Product takeProduct(int ticketId);
}