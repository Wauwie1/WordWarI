package Client.GUIControllers;

import javafx.fxml.FXMLLoader;

public class LoaderFactory {
    public FXMLLoader getLoader(Scenes sceneName) {
        switch (sceneName) {
            case LOGINSCENE:
                return new FXMLLoader(getClass().getResource("/login.fxml"));
            case LOBBYSCENE:
                return new FXMLLoader(getClass().getResource("/lobby.fxml"));
            case GAMESCENE:
                return new FXMLLoader(getClass().getResource("/game.fxml"));
                default:
                    System.out.println("Unknown scene name.");
                    return null;
        }

    }
}
