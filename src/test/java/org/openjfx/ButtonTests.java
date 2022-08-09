package org.openjfx;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.openjfx.app.GameApp;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.NodeQueryUtils;



public class ButtonTests extends ApplicationTest {
    private final GameApp gameApp = new GameApp();

    @Override
    public void start(Stage stage) {
        gameApp.start(stage);
    }

    @Test
    public void hasWelcomeScreenButton() {
        FxAssert.verifyThat("#button", NodeQueryUtils.hasText("START"));
    }

    @Test
    public void clickWelcomeScreenButton() {
        clickOn("#button");
    }
}
