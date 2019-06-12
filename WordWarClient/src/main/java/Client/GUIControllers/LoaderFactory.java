package Client.GUIControllers;

import javafx.fxml.FXMLLoader;
import lombok.extern.log4j.Log4j;

@Log4j
public class LoaderFactory {
    public FXMLLoader getLoader(Scenes sceneName) {
        if(sceneName == null) {
            return null;
        }

        switch (sceneName) {
            case LOGINSCENE:
                return new FXMLLoader(getClass().getResource("/login.fxml"));
            case LOBBYSCENE:
                return new FXMLLoader(getClass().getResource("/lobby.fxml"));
            case GAMESCENE:
                return new FXMLLoader(getClass().getResource("/game.fxml"));
                default:
                    log.error("Unknown scene name.");
                    return null;
        }

    }
}
