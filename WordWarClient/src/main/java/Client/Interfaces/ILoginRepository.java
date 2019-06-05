package Client.Interfaces;

import Models.User;

public interface ILoginRepository {

    User login(String username, String password);
    boolean register();
}
