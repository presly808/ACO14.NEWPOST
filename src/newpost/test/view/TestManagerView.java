package newpost.test.view;

import newpost.db.AppDataContainer;
import newpost.db.InitDB;
import newpost.view.ManagerView;

/**
 * Created by Serhii Fursenko on 17.08.16.
 * mail to fyrsenko@gmail.com
 */
public class TestManagerView {

    public static void main(String[] args) {

        AppDataContainer appDataContainer = new AppDataContainer();
        InitDB.initDB(appDataContainer);
        ManagerView managerView = new ManagerView(appDataContainer);

        managerView.showManagerView();
    }
}
