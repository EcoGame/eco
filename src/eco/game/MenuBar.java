package eco.game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class MenuBar {

    /**
     *
     * This class manages the rendering and logic for the menu bar
     *
     * @author phil, connor
     *
     */

    // ===============//
    // Pane Variables //
    // ===============//
    private static int pane = 0;

    private static ToggleButton statsPane = new ToggleButton(0,
            10 + (6f * Main.height / 8f), 32, 6, 2, 7, 2, true);
    private static ToggleButton econPane = new ToggleButton(0,
            10 + (6f * Main.height / 8f) + 32, 32, 6, 2, 7, 2, false);
    private static ToggleButton warPane = new ToggleButton(0,
            10 + (6f * Main.height / 8f) + 64, 32, 6, 2, 7, 2, false);
    private static ToggleButton logPane = new ToggleButton(0,
            10 + (6f * Main.height / 8f) + 96, 32, 6, 2, 7, 2, false);
    private static ToggleButton graphsPane = new ToggleButton(0,
            10 + (6f * Main.height / 8f) + 128, 32, 6, 2, 7, 2, false);

    private static ArrayList<Button> buttons = new ArrayList<Button>();
    private static TextureAtlas atlas = Render.atlas;

    // =============//
    // Control Pane //
    // =============//


    private static ArrayList<Button> pane0Buttons = new ArrayList<Button>();

    // =========//
    // War Pane //
    // =========//
    private static Button warUp = new Button(40, 575, 48, 0, 2, 1, 2);
    private static Button warDown = new Button(40, 645, 48, 2, 2, 3, 2);

    private static TextButton warOne = new TextButton(100,
            (Main.height / 8f * 6f) + 16, 176, 20, 6, 2, 7, 2, "Declare War");
    private static TextButton warTwo = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 1), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warThree = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 2), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warFour = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 3), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warFive = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 4), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warSix = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 5), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warSeven = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 6), 176, 20, 6, 2, 7, 2,
            "Declare War");
    private static TextButton warEight = new TextButton(100,
            (Main.height / 8f * 6f) + 16 + (20 * 7), 176, 20, 6, 2, 7, 2,
            "Declare War");

    private static ArrayList<Button> pane1Buttons = new ArrayList<Button>();

    private static int warOff = 0;
    private static int warPos;

    private static int warState = 2;
    private static String warName = "";
    private static int gold;
    private static int land;
    private static int wheat;

    // =============//
    // Logging Pane //
    // =============//
    private static Button logUp = new Button(40, 575, 48, 0, 2, 1, 2);
    private static Button logDown = new Button(40, 645, 48, 2, 2, 3, 2);

    private static ArrayList<Button> pane2Buttons = new ArrayList<Button>();

    private static int logOff = 0;
    private static int logPos;

    // =======//
    // Graphs //
    // =======//

    private static Graph popGraph = new Graph(75, 710, "Population",
            new Treble<>(0f, 153f / 255f, 204f / 255f));
    private static Graph globalWheatGraph = new Graph(75 + (150 * 1) + 75, 710,
            "World Wheat", new Treble<>(208f / 255f, 194f / 255f, 109f / 255f));
    private static Graph moneyGraph = new Graph(75 + (150 * 2) + (75 * 2), 710,
            "Treasury", new Treble<>(100f / 255f, 191f / 255f, 115f / 255f));
    private static Graph scoreGraph = new Graph(75 + (150 * 3) + (75 * 3), 710,
            "Score", new Treble<>(167 / 255f, 76 / 255f, 76 / 255f));
    private static Graph scoreGraphOther = new Graph(75 + (150 * 3) + (75 * 3),
            710, "Score (Other)",
            new Treble<>(129 / 255f, 87 / 255f, 87 / 255f));

    private static ArrayList<Graph> graphs = new ArrayList<Graph>();
    
    // =============//
    // Economy Pane //
    // =============//
    private static ToggleButton toggleFeedDisplaced = new ToggleButton(900,
            657, 25, 4, 2, 5, 2, true);
    private static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627,
            25, 4, 2, 5, 2, false);

    private static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2,
            1, 2);
    private static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2,
            3, 2);

    private static ToggleButton toggleCutTrees = new ToggleButton(900, 597, 25,
            4, 2, 5, 2, true);

    private static ToggleButton toggleForceConscription = new ToggleButton(900,
            567, 25, 4, 2, 5, 2, true);

    private static ToggleButton toggleVeryFast = new ToggleButton(35, 595, 25, 8, 5, 9, 5,
             false);
    private static ToggleButton toggleFast = new ToggleButton(70, 595, 25, 8, 5, 9, 5,
     false);
    private static ToggleButton toggleNormal = new ToggleButton(105, 595, 25, 8, 6, 9, 6,
     true);
    private static ToggleButton toggleSlow = new ToggleButton(140, 595, 25, 8, 6, 9, 6,
     false);
    private static ToggleButton togglePaused = new ToggleButton(175, 595, 25, 8, 4, 9, 4,
             false);
    
    private static ArrayList<Button> pane4Buttons = new ArrayList<Button>();

    static {
        buttons.add(statsPane);
        buttons.add(warPane);
        buttons.add(graphsPane);
        buttons.add(logPane);
        buttons.add(econPane);

        pane4Buttons.add(toggleFeedDisplaced);
        pane4Buttons.add(toggleFavorWarrior);
        pane4Buttons.add(increaseWarriorRatio);
        pane4Buttons.add(decreaseWarriorRatio);
        pane4Buttons.add(toggleFast);
        pane4Buttons.add(toggleSlow);
        pane4Buttons.add(toggleNormal);
        pane4Buttons.add(toggleVeryFast);
        pane4Buttons.add(togglePaused);
        pane4Buttons.add(toggleCutTrees);
        pane4Buttons.add(toggleForceConscription);

        pane1Buttons.add(warUp);
        pane1Buttons.add(warDown);
        pane1Buttons.add(warOne);
        pane1Buttons.add(warTwo);
        pane1Buttons.add(warThree);
        pane1Buttons.add(warFour);
        pane1Buttons.add(warFive);
        pane1Buttons.add(warSix);
        pane1Buttons.add(warSeven);
        pane1Buttons.add(warEight);

        pane2Buttons.add(logUp);
        pane2Buttons.add(logDown);

        graphs.add(popGraph);
        graphs.add(globalWheatGraph);
        graphs.add(moneyGraph);
        graphs.add(scoreGraph);
        graphs.add(scoreGraphOther);
        scoreGraphOther.hideStats();

        statsPane.addOverlay(0, 1, 24);
        warPane.addOverlay(1, 1, 24);
        logPane.addOverlay(1, 5, 24);
        graphsPane.addOverlay(0, 5, 24);
        econPane.addOverlay(8, 0, 24);
    }

    public static void update(PlayerCountry playerCountry) {
        if (statsPane.checkForClick()) {
            warPane.setToggle(false);
            graphsPane.setToggle(false);
            logPane.setToggle(false);
            statsPane.setToggle(true);
            econPane.setToggle(false);
            pane = 0;
        }
        if (warPane.checkForClick()) {
            statsPane.setToggle(false);
            graphsPane.setToggle(false);
            logPane.setToggle(false);
            warPane.setToggle(true);
            econPane.setToggle(false);
            pane = 1;
        }
        if (logPane.checkForClick()) {
            statsPane.setToggle(false);
            graphsPane.setToggle(false);
            warPane.setToggle(false);
            logPane.setToggle(true);
            econPane.setToggle(false);
            pane = 2;
        }
        if (graphsPane.checkForClick()) {
            statsPane.setToggle(false);
            logPane.setToggle(false);
            warPane.setToggle(false);
            graphsPane.setToggle(true);
            econPane.setToggle(false);
            pane = 3;
        }
        if (econPane.checkForClick()) {
            statsPane.setToggle(false);
            logPane.setToggle(false);
            warPane.setToggle(false);
            graphsPane.setToggle(false);
            econPane.setToggle(true);
            pane = 4;
        }

        if (pane == 4) {
            if (toggleFeedDisplaced.checkForClick()) {
                playerCountry.displacedEat ^= true;
            }
            if (toggleFavorWarrior.checkForClick()) {
                playerCountry.favorFarmers ^= true;
            }
            if (toggleCutTrees.checkForClick()) {
                World.cutForests ^= true;
            }
            if (toggleForceConscription.checkForClick()) {
                playerCountry.forceConscription ^= true;
            }
            if (increaseWarriorRatio.checkForClick()) {
                if (playerCountry.desiredWarriorRatio < 1.0f) {
                    playerCountry.desiredWarriorRatio += 0.01f;
                }
            }
            if (decreaseWarriorRatio.checkForClick()) {
                if (playerCountry.desiredWarriorRatio > 0.0f) {
                    playerCountry.desiredWarriorRatio -= 0.01f;
                } else {
                    playerCountry.desiredWarriorRatio = 0.0f;
                }
            }
            if (toggleFast.checkForClick()) {
                Main.framesPerTick = 9;
                toggleFast.setToggle(true);
                toggleSlow.setToggle(false);
                toggleNormal.setToggle(false);
                togglePaused.setToggle(false);
                toggleVeryFast.setToggle(false);
            }
            if (toggleSlow.checkForClick()) {
                Main.framesPerTick = 36;
                toggleFast.setToggle(false);
                toggleSlow.setToggle(true);
                toggleNormal.setToggle(false);
                togglePaused.setToggle(false);
                toggleVeryFast.setToggle(false);
            }
            if (toggleNormal.checkForClick()) {
                Main.framesPerTick = 18;
                toggleFast.setToggle(false);
                toggleSlow.setToggle(false);
                toggleNormal.setToggle(true);
                togglePaused.setToggle(false);
                toggleVeryFast.setToggle(false);
            }
            if (toggleVeryFast.checkForClick()) {
                Main.framesPerTick = 3;
                toggleFast.setToggle(false);
                toggleSlow.setToggle(false);
                toggleNormal.setToggle(false);
                togglePaused.setToggle(false);
                toggleVeryFast.setToggle(true);
            }
            if (togglePaused.checkForClick()) {
                Main.framesPerTick = 0x2000000;
                toggleFast.setToggle(false);
                toggleSlow.setToggle(false);
                toggleNormal.setToggle(false);
                togglePaused.setToggle(true);
                toggleVeryFast.setToggle(false);
            }
        }

       /* if (pane == 1) {
            if (warDown.checkForClick()) {
                warOff++;
                warOff = Math.min(warOff, PlayerCountry.countries.size());
            }
            if (warUp.checkForClick()) {
                warOff--;
                if (warOff < 0) {
                    warOff = 0;
                }
            }
            if (warOff != 0 && warPos == 0) {
                warPos = playerCountry.countries.size();
            } else if (warOff == 0) {
                warPos = 0;
            }
            try {
                if (warOne.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 1);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warTwo.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 2);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warThree.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 3);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warFour.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 4);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warFive.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 5);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warSix.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 6);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warSeven.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 7);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
                if (warEight.checkForClick()) {
                    NPCCountry c = playerCountry.countries
                            .get(playerCountry.countries.size() - warOff - 8);
                    if (c != null && !c.dead) {
                        War.warWith(c);
                        warName = "The " + playerCountry.name + "-" + c.name
                                + " war";
                        updateWar(warName);
                    }
                }
            } catch (Exception e) {

            }
        }*/

        if (pane == 2) {
            if (logDown.checkForClick()) {
                logOff++;
                logOff = Math.min(logOff, EventLog.getAll().size());
            }
            if (logUp.checkForClick()) {
                logOff--;
                if (logOff < 0) {
                    logOff = 0;
                }
            }
            if (logOff != 0 && logPos == 0) {
                logPos = EventLog.getAll().size();
            } else if (logOff == 0) {
                logPos = 0;
            }
        }

        popGraph.tick(playerCountry.farmer.getfPop()
                + playerCountry.warrior.getwPop());
        globalWheatGraph.tick(playerCountry.wheat.gettWheat());
        moneyGraph.tick(playerCountry.economy.getTreasury());
        scoreGraph.tick(playerCountry.score.scoreAt(Math.max(0,
                playerCountry.year - 1)));
        scoreGraphOther.tick(MathUtil.calcAverageCountryScore());

    }

    public static void click(float x, float y) {
        for (Button b : buttons) {
            b.click(x, y);
        }
        if (pane == 0) {
            for (Button b : pane0Buttons) {
                b.click(x, y);
            }
        }
        if (pane == 1) {
            for (Button b : pane1Buttons) {
                b.click(x, y);
            }
        }
        if (pane == 2) {
            for (Button b : pane2Buttons) {
                b.click(x, y);
            }
        }
        if (pane == 4) {
            for (Button b : pane4Buttons) {
                b.click(x, y);
            }
        }
    }

    public static void render(float x, float y) {
        atlas.getTexture().bind();
        for (Button b : buttons) {
            b.render(x, y);
        }
        if (pane == 0) {
            renderStats(PlayerCountry.playerCountry);
        } else if (pane == 1) {
            renderWar(PlayerCountry.playerCountry);
        } else if (pane == 2) {
            renderLog(PlayerCountry.playerCountry);
        } else if (pane == 3) {
            renderGraphs(PlayerCountry.playerCountry);
        } else if (pane == 4){
            renderEcon(PlayerCountry.playerCountry);
        }
    }

    public static void render2(float x, float y) {
        RenderUtil.initOrtho();
        for (Button b : buttons) {
            b.render2();
        }
        if (pane == 3) {
            renderGraphs2(PlayerCountry.playerCountry);
        }
    }
    
    public static void renderEcon(PlayerCountry playerCountry){
        for (Button b : pane4Buttons){
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }
        
        renderEcon2(playerCountry);
    }
    
    public static void renderEcon2(PlayerCountry playerCountry){
        for (Button b : pane4Buttons){
            b.render2();
        }
        
        RenderUtil.drawString("Conscription Rate: "
                        + ((int) (100 * playerCountry.desiredWarriorRatio)) + "%", 285,
                657);
        if(toggleFast.getToggle() == true) {
            RenderUtil.drawString("Game Speed: Fast", 35, 567);
        } else if(toggleSlow.getToggle() == true) {
            RenderUtil.drawString("Game Speed: Slow", 35, 567);
        } else if(toggleNormal.getToggle() == true) {
            RenderUtil.drawString("Game Speed: Normal", 35, 567);
        } else if(toggleVeryFast.getToggle() == true) {
            RenderUtil.drawString("Game Speed: Very Fast", 35, 567);
        } else if(togglePaused.getToggle() == true) {
            RenderUtil.drawString("Game Speed: Paused", 35, 567);
        }
        RenderUtil.drawString(
                "Feed Displaced: " + String.valueOf(playerCountry.displacedEat),
                585, 657);
        RenderUtil.drawString(
                "Favor Warrior Rations: "
                        + String.valueOf(!playerCountry.favorFarmers), 585, 627);
        RenderUtil.drawString("Cut Forests: " + String.valueOf(World.cutForests),
                585, 597);
        RenderUtil.drawString(
                "Force Conscription: "
                        + String.valueOf(playerCountry.forceConscription), 585,
                567);
    }

    public static void renderWar(PlayerCountry playerCountry) {
        for (int i = 0; i < pane1Buttons.size(); i++) {
            if (i < 2) {
                pane1Buttons.get(i).render(Mouse.getX(),
                        Main.height - Mouse.getY());
                continue;
            }
            try {
                /*NPCCountry c = PlayerCountry.countries.get(PlayerCountry.countries
                        .size() - warOff - (i - 2) - 1);
                if (c == null) {
                    continue;
                }*/
            } catch (Exception e) {
                continue;
            }
            pane1Buttons.get(i)
                    .render(Mouse.getX(), Main.height - Mouse.getY());
        }

        if (warState != 0 && warState != 2) {
            /* Draw wheat icon */
            glBegin(GL_QUADS);
            glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
            glVertex2f(750, 575);
            glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
            glVertex2f(775, 575);
            glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
            glVertex2f(775, 600);
            glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
            glVertex2f(750, 600);
            glEnd();
            glColor3f(1.0f, 1.0f, 1.0f);

            /* Draw wheat icon */
            glBegin(GL_QUADS);
            glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, false));
            glVertex2f(750, 610);
            glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, false));
            glVertex2f(775, 610);
            glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, true));
            glVertex2f(775, 635);
            glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, true));
            glVertex2f(750, 635);
            glEnd();
            glColor3f(1.0f, 1.0f, 1.0f);

            /* Draw wheat icon */
            glBegin(GL_QUADS);
            glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, false));
            glVertex2f(750, 645);
            glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, false));
            glVertex2f(775, 645);
            glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, true));
            glVertex2f(775, 670);
            glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, true));
            glVertex2f(750, 670);
            glEnd();
            glColor3f(1.0f, 1.0f, 1.0f);
        }

        renderWar2(playerCountry);
    }

    public static void renderGraphs(PlayerCountry playerCountry) {
        for (Graph g : graphs) {
            g.render();
        }
    }

    public static void renderGraphs2(PlayerCountry playerCountry) {
        for (Graph g : graphs) {
            g.render2();
        }
    }

    public static void renderLog(PlayerCountry playerCountry) {

        for (Button b : pane2Buttons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }

        ArrayList<String> log = EventLog.getAll();

        String str;
        int offx = 100;
        int offy = Main.height / 8 * 6;
        offy += 20;

        int logstart = logOff;

        for (int i = logstart; i < 8 + logstart; i++) {
            try {
                if (logOff == 0) {
                    str = log.get(log.size() - 1 - i);
                } else {
                    str = log.get(logPos - 1 - i);
                }
                RenderUtil.font.drawString(offx, offy, str);
                offy += 20;
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void renderWar2(PlayerCountry playerCountry) {
        for (int i = 0; i < pane1Buttons.size(); i++) {
            if (i < 2) {
                pane1Buttons.get(i).render2();
                continue;
            }
            try {
               /* NPCCountry c = PlayerCountry.countries.get(PlayerCountry.countries
                        .size() - warOff - (i - 2) - 1);
                if (c == null) {
                    continue;
                }*/
            } catch (Exception e) {
                continue;
            }
            pane1Buttons.get(i).render2();
        }

        int offx = 300;
        int offy = Main.height / 8 * 6;
        offy += 20;

        int logstart = warOff;

        for (int i = logstart; i < 8 + logstart; i++) {
            try {
                String name = "";
                NPCCountry NPCCountry = null;
               /* if (warOff == 0) {
                    name = PlayerCountry.countries.get(PlayerCountry.countries
                            .size() - 1 - i).name;
                    NPCCountry = PlayerCountry.countries
                            .get(PlayerCountry.countries.size() - 1 - i);
                } else {
                    name = PlayerCountry.countries.get(warPos - 1 - i).name;
                    NPCCountry = PlayerCountry.countries.get(warPos - 1 - i);
                }*/
                if (!NPCCountry.dead) {
                    RenderUtil.font.drawString(offx, offy, name);
                    RenderUtil.font.drawString(offx + RenderUtil.font.getWidth(name)
                            + 10, offy,
                            "[" + Integer.toString(NPCCountry.aggression.aggressionScore)
                                    + "]", new Color(
                                    (NPCCountry.aggression.aggressionScore * 2) / 255f,
                                    1 - (NPCCountry.aggression.aggressionScore * 2 / 255f),
                                    20 / 255f));
                    int tempoff = offx
                            + RenderUtil.font.getWidth(name)
                            + 20
                            + RenderUtil.font.getWidth("["
                                    + Integer
                                            .toString(NPCCountry.aggression.aggressionScore)
                                    + "]");
                    RenderUtil.font.drawString(tempoff, offy,
                            "[" + Integer.toString(NPCCountry.getScore()) + "]",
                            new Color(102, 186, 233));
                    offy += 20;
                } else {
                    RenderUtil.font.drawString(offx, offy, name);
                    RenderUtil.font.drawString(offx + RenderUtil.font.getWidth(name)
                            + 10, offy,
                            "[" + Integer.toString(NPCCountry.aggression.aggressionScore)
                                    + "]", new Color(
                                    (NPCCountry.aggression.aggressionScore * 2) / 255f,
                                    1 - (NPCCountry.aggression.aggressionScore * 2 / 255f),
                                    20 / 255f));
                    int tempoff = offx
                            + RenderUtil.font.getWidth(name)
                            + 20
                            + RenderUtil.font.getWidth("["
                                    + Integer
                                            .toString(NPCCountry.aggression.aggressionScore)
                                    + "]");
                    RenderUtil.font.drawString(tempoff, offy, "[In Exile]",
                            new Color(195, 65, 65));
                    offy += 20;
                }
            } catch (Exception e) {
                break;
            }
        }

        if (warState == 0) {
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
            int off = RenderUtil.font.getWidth(warName) + 10;
            RenderUtil.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
                    "- Stalemate!", new Color(0.75f, 0.75f, 0.25f));
        } else if (warState == 1) {
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
            int off = RenderUtil.font.getWidth(warName) + 10;
            RenderUtil.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
                    "- Victory!", new Color(0.25f, 0.75f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 37, "Wheat: "
                    + wheat, new Color(0.25f, 0.75f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 77, "Money: "
                    + gold, new Color(0.25f, 0.75f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 117, "Land: "
                    + land, new Color(0.25f, 0.75f, 0.25f));
        } else if (warState == -1) {
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
            int off = RenderUtil.font.getWidth(warName) + 10;
            RenderUtil.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
                    "- Defeat!", new Color(0.75f, 0.25f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 37, "Wheat: "
                    + wheat, new Color(0.75f, 0.25f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 77, "Money: "
                    + gold, new Color(0.75f, 0.25f, 0.25f));
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 117, "Land: "
                    + land, new Color(0.75f, 0.25f, 0.25f));
        } else if (warState == 2) {
            RenderUtil.font.drawString(800, (Main.height / 8 * 6) + 12,
                    "No Recent Wars");
        }

    }

    public static void renderStats(PlayerCountry playerCountry) {

        for (Button b : pane0Buttons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }

        /* Draw wheat icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
        glVertex2f(50, 575);
        glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
        glVertex2f(75, 575);
        glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
        glVertex2f(75, 600);
        glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
        glVertex2f(50, 600);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw house icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, false));
        glVertex2f(50, 610);
        glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, false));
        glVertex2f(75, 610);
        glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, true));
        glVertex2f(75, 635);
        glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, true));
        glVertex2f(50, 635);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw castle icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, false));
        glVertex2f(50, 645);
        glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, false));
        glVertex2f(75, 645);
        glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, true));
        glVertex2f(75, 670);
        glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, true));
        glVertex2f(50, 670);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw log icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(7, false), atlas.getCoord(4, false));
        glVertex2f(950, 593);
        glTexCoord2f(atlas.getCoord(7, true), atlas.getCoord(4, false));
        glVertex2f(975, 593);
        glTexCoord2f(atlas.getCoord(7, true), atlas.getCoord(4, true));
        glVertex2f(975, 617);
        glTexCoord2f(atlas.getCoord(7, false), atlas.getCoord(4, true));
        glVertex2f(950, 617);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw sword icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(3, false));
        glVertex2f(950, 563);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(3, false));
        glVertex2f(975, 563);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(3, true));
        glVertex2f(975, 587);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(3, true));
        glVertex2f(950, 587);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw coin icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, false));
        glVertex2f(950, 653);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, false));
        glVertex2f(975, 653);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, true));
        glVertex2f(975, 677);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, true));
        glVertex2f(950, 677);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw land icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, false));
        glVertex2f(950, 623);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, false));
        glVertex2f(975, 623);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, true));
        glVertex2f(975, 647);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, true));
        glVertex2f(950, 647);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        /* Draw stone icon */
        glBegin(GL_QUADS);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(2, false));
        glVertex2f(950, 683);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(2, false));
        glVertex2f(975, 683);
        glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(2, true));
        glVertex2f(975, 707);
        glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(2, true));
        glVertex2f(950, 707);
        glEnd();
        glColor3f(1.0f, 1.0f, 1.0f);

        renderStats2(playerCountry);

    }

    public static void renderStats2(PlayerCountry playerCountry) {
        RenderUtil.drawString(String.valueOf(playerCountry.warrior.getwPop())
                + " Warriors", 85, 657);
        RenderUtil.drawString(String.valueOf(playerCountry.farmer.getfPop())
                + " Farmers", 85, 627);
        RenderUtil.drawString(String.valueOf(playerCountry.wheat.gettWheat()), 85,
                587);
        if (MathUtil.getWheatRate(playerCountry) > 0) {
            RenderUtil.font.drawString(85 + RenderUtil.font.getWidth(String
                    .valueOf(playerCountry.wheat.gettWheat() + " ")), 587, " ("
                    + MathUtil.getWheatRateForDisplay(playerCountry) + ")", Color.green);
        } else {
            RenderUtil.font.drawString(85 + RenderUtil.font.getWidth(String
                    .valueOf(playerCountry.wheat.gettWheat() + " ")), 587, " ("
                    + MathUtil.getWheatRateForDisplay(playerCountry) + ")", Color.red);
        }
        RenderUtil.drawString(
                "Occupied Territories: "
                        + String.valueOf(playerCountry.land.getLand()), 985,
                627);
        RenderUtil.drawString(
                "Money: " + String.valueOf(playerCountry.economy.getTreasury()),
                985, 657);
        RenderUtil.drawString(
                "Wood: " + String.valueOf(playerCountry.wood.getWood()), 985,
                597);
        RenderUtil.drawString("Aggression: ", 985, 567);
        int value = playerCountry.aggression.aggressionScore;
        RenderUtil.font.drawString(985 + 15 + RenderUtil.font.getWidth("Aggression"),
                567, String.valueOf(playerCountry.aggression.aggressionScore), new Color(
                        (value * 2) / 255f, 1 - (value * 2 / 255f), 20 / 255f));
        RenderUtil.drawString(
                "Stone: " + String.valueOf(playerCountry.stone.getStone()),
                985, 687);
    }

    public static boolean shouldRenderGraphs() {
        return pane == 3;
    }

    public static void reset() {
        warState = 2;
        wheat = 0;
        gold = 0;
        land = 0;
        for (Graph g : graphs) {
            g.reset();
        }
    }

    public static void setPane(int newPane){
        pane = newPane;
    }

    public static void updateWar(String name){
        warState = War.winLose;
        warName = name;
        wheat = War.wheatLoss;
        gold = War.moneyLoss;
        land = War.landLoss;
    }

}
