package newpost.view.frame;

import com.lynden.gmapsexampleapp.FXMLController;
import newpost.model.common.Address;
import newpost.model.common.MyDate;
import newpost.model.common.Product;
import newpost.model.office.Client;
import newpost.model.office.PostTicket;
import newpost.utils.geolocation.controller.GoogleMapsAPI;
import newpost.utils.geolocation.controller.GoogleMapsAPIImpl;
import newpost.utils.geolocation.controller.Location;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by sasha on 20.08.2016.
 */
public class TicketInfoView extends JFrame {

    private PostTicket postTicket;

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;
    private static final String TITLE = "ticket info";
    private static final String[] productTableHeader = {"product name", "price", "length, sm", "width, sm",
            "height, sm", "weight, gm"};
    private final GoogleMapsAPI googleMapsAPI;

    public TicketInfoView(PostTicket postTicket) {
        googleMapsAPI = new GoogleMapsAPIImpl();

        //this.postTicket = postTicket;
        JFrame main = createMainFrame();
        Box box = Box.createVerticalBox();
        box.add(new JLabel("ticket ID: " + postTicket.getId()));
        box.add(createClientInfoPanel(postTicket.getClient()));
        box.add(adressPanel(postTicket.getFrom(), postTicket.getTo()));
        box.add(datePanel(postTicket.getCreationDate(), postTicket.getEstimationArrivalDate()));
        box.add(productPanel(postTicket.getProducts()));

        Button showOnMapButton = new Button("Show On Map");
        showOnMapButton.addActionListener((event) -> {

            Address to = postTicket.getTo();
            Location location = googleMapsAPI.findLocation("Ukraine", to.getCity(), to.getStreet(), to.getHouseNum());

            new Thread(() -> {
                        FXMLController.main(new String[]{"lat1:" + location.getLat(),
                                "long1:" + location.getLng(),
                                "title1:" + postTicket.getId() + "\n" + postTicket.getStatus()});
                    }
            ).start();

        });

        box.add(showOnMapButton);

        main.setContentPane(box);
        main.setVisible(true);
    }


    private static JFrame createMainFrame() {
        JFrame managerFrame = new JFrame(TITLE);
        managerFrame.setIconImage(new ImageIcon("resources/icons/managerViewIcon.png").getImage());
        managerFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        managerFrame.setResizable(false);
        managerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        managerFrame.setLocation(100, 100);
        managerFrame.setLocationRelativeTo(null);

        return managerFrame;
    }


    private JPanel createClientInfoPanel(Client client) {
        JPanel panelClientInfo = new JPanel(null);

        panelClientInfo.setLayout(new GridLayout(2, 2));
        panelClientInfo.setBorder(new CompoundBorder(new EmptyBorder(12, 12, 12, 12), new TitledBorder("Client info")));
        panelClientInfo.setBounds(20, 15, 550, 70);
        panelClientInfo.add(new JLabel("full name: " + client.getPassport().getFullname()));
        panelClientInfo.add(new JLabel("passport Id: " + client.getPassport().getNumber()));
        panelClientInfo.add(new JLabel("phone number: " + client.getPhone()));
        panelClientInfo.add(new JLabel("email: " + client.getMail()));

        return panelClientInfo;
    }

    private JPanel adressPanel(Address addressFrom, Address addressTo) {
        JPanel panelAddress = new JPanel(null);

        panelAddress.setLayout(new GridLayout(3, 2));
        panelAddress.setBorder(new CompoundBorder(new EmptyBorder(12, 12, 12, 12), new TitledBorder("address from / to")));
        panelAddress.setBounds(20, 85, 550, 90);
        panelAddress.add(new JLabel("City from: " + addressFrom.getCity()));
        panelAddress.add(new JLabel("Street from: " + addressFrom.getStreet()));
        panelAddress.add(new JLabel("HouseNum from: " + addressFrom.getHouseNum()));
        panelAddress.add(new JLabel("City to: " + addressTo.getCity()));
        panelAddress.add(new JLabel("Street to: " + addressTo.getStreet()));
        panelAddress.add(new JLabel("HouseNum to: " + addressTo.getHouseNum()));

        return panelAddress;
    }


    private JPanel datePanel(MyDate dateStart, MyDate dateEnd) {
        JPanel panelDate = new JPanel(null);

        panelDate.setLayout(new GridLayout(1, 2));
        panelDate.setBorder(new CompoundBorder(new EmptyBorder(12, 12, 12, 12), new TitledBorder("Date start / finish")));
        panelDate.setBounds(20, 175, 550, 70);
        String strDateStart = String.valueOf(dateStart.getYear()) + " - "
                + String.valueOf(dateStart.getMonth()) + " - "
                + String.valueOf(dateStart.getDay());

        String strDateFin = String.valueOf(dateEnd.getYear()) + " - "
                + String.valueOf(dateEnd.getMonth()) + " - "
                + String.valueOf(dateEnd.getDay());

        panelDate.add(new JLabel("date start: " + strDateStart));
        panelDate.add(new JLabel("date estimation: " + strDateFin.toString()));
        return panelDate;
    }

    private JPanel productPanel(Product[] products) {

        Object[][] data = new Object[products.length][productTableHeader.length];
        for (int i = 0; i < products.length; i++) {
            data[i][0] = products[i].getName();
            data[i][1] = products[i].getPrice();
            data[i][2] = products[i].getSize().getLength();
            data[i][3] = products[i].getSize().getWidth();
            data[i][4] = products[i].getSize().getHeight();
            data[i][5] = products[i].getSize().getWeight();
        }
        JTable productsTable = new JTable(data, productTableHeader);
        JScrollPane prodScrollTable = new JScrollPane(productsTable);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(1, 1));
        productPanel.setBorder(new CompoundBorder(new EmptyBorder(12, 12, 12, 12), new TitledBorder("product list")));
        productPanel.setBounds(20, 170, 550, 500);
        productPanel.add(prodScrollTable);

        return productPanel;
    }


}
