package eco.ui;

import eco.game.Main;
import eco.game.PlayerCountry;
import eco.game.SaveUtil;

/**
 * A final class that holds various click events
 *
 * @author phil
 *
 */
public final class ClickEvents {

    public static final IClickEvent startGameOne = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            UIManager.startGame(1, (TextButton) button);
        }
    };

    public static final IClickEvent startGameTwo = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            UIManager.startGame(2, (TextButton) button);
        }
    };

    public static final IClickEvent startGameThree = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            UIManager.startGame(3, (TextButton) button);
        }
    };

    public static final IClickEvent startGameFour = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            UIManager.startGame(4, (TextButton) button);
        }
    };

    public static final IClickEvent startGameFive = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            UIManager.startGame(5, (TextButton) button);
        }
    };

    public static final IClickEvent deleteSave1 = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            SaveUtil.deleteSave(1);
        }
    };

    public static final IClickEvent deleteSave2 = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            SaveUtil.deleteSave(2);
        }
    };

    public static final IClickEvent deleteSave3 = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            SaveUtil.deleteSave(3);
        }
    };

    public static final IClickEvent deleteSave4 = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            SaveUtil.deleteSave(4);
        }
    };

    public static final IClickEvent deleteSave5 = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            SaveUtil.deleteSave(5);
        }
    };

    public static final IClickEvent genIslands = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            PlayerCountry.generatorToUse = 0;
            UIManager.generatorArchipelago.setToggle(false);
            UIManager.generatorMountains.setToggle(false);
        }
    };

    public static final IClickEvent genArchipelago = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            PlayerCountry.generatorToUse = 1;
            UIManager.generatorIsland.setToggle(false);
            UIManager.generatorMountains.setToggle(false);
        }
    };

    public static final IClickEvent genMountains = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            PlayerCountry.generatorToUse = 2;
            UIManager.generatorIsland.setToggle(false);
            UIManager.generatorArchipelago.setToggle(false);
        }
    };

    public static final IClickEvent exitToMenu = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            Main.shouldBeInMenu = true;
            Main.shouldQuit = true;
        }
    };

    public static final IClickEvent loadLastSave = new IClickEvent() {
        @Override
        public void onClick(Button button) {
            Main.gameOver = false;
            SaveUtil.readSave();
        }
    };

    public static final IClickEvent dummy = new IClickEvent() {
        @Override
        public void onClick(Button button) {}
    };


}
