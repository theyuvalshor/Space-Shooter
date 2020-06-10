package com.spaceshooter.view;

import com.spaceshooter.controller.Controller;
import com.spaceshooter.model.LeaderboardData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private Controller gameController;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private LeaderboardsPanel leaderboardsPanel;
    final private JPanel cardPanel;

    public GameWindow(int width, int height) {
        setTitle("Space Shooter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(false);

        setLayout(new BorderLayout());

        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());

        menuPanel = new MenuPanel(width, height);
        gamePanel = new GamePanel(width, height);
        leaderboardsPanel = new LeaderboardsPanel(width,height);

        cardPanel.add(gamePanel, "gamepanel");
        cardPanel.add(menuPanel, "menupanel");
        cardPanel.add(leaderboardsPanel, "leaderboardpanel");

        add(cardPanel, BorderLayout.CENTER);

        pack();
        addListeners();

        CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());
        cardLayout.show(cardPanel, "menupanel");
    }

    public void setController(Controller gameController) {
        this.gameController = gameController;
    }

    private void addListeners() {
        gamePanel.setPanelMouseMovementListener(new PanelMouseMovementListener() {
            @Override
            public void mouseMovedInPanel(int playerX, int playerY) {
                if(gameController != null){
                    gameController.updatePlayerPosition(playerX, playerY);
                }
            }
        });

        gamePanel.setPanelMouseClickListener(new PanelMouseClickListener() {
            @Override
            public void mouseClickedOnPanel() {
                if(gameController != null) {
                    gameController.playerMouseClicked();
                }
            }
        });

        gamePanel.setGamePanelKeyInputListener(new GamePanelKeyInputListener() {
            @Override
            public void enterKeyPressed() {
                if(gameController.attemptStopGame()) {
                    CardLayout cardLayout = (CardLayout) (cardPanel.getLayout());
                    cardLayout.show(cardPanel, "menupanel");
                    pack();
                }
            }
        });

        menuPanel.setMenuPlayButtonClickListener(new MenuPlayButtonClickListener() {
            @Override
            public void mouseButtonClick() {
                CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());
                cardLayout.show(cardPanel, "gamepanel");
                gamePanel.CreateGamePanelBufferStrategy(2);
                pack();
                gameController.startGame();
            }
        });

        menuPanel.setMenuLeaderboardsClickListener(new MenuLeaderboardsClickListener() {
            @Override
            public void mouseButtonClick() {
                CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());
                cardLayout.show(cardPanel, "leaderboardpanel");
                leaderboardsPanel.drawLeaderboardsTable();
                pack();
            }
        });

        menuPanel.setMenuExitClickListener(new MenuExitButtonClickListener() {
           @Override
           public void mouseButtonClick() {
               System.exit(0);
           }
        });

        leaderboardsPanel.setLeaderboardsBackClickListener(new BackToMenuButtonClickListener(){
            @Override
            public void mouseButtonClick() {
                CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());
                cardLayout.show(cardPanel, "menupanel");
                pack();
            }
        });

        leaderboardsPanel.setLeaderboardDataListener(new LeaderboardDataListener() {
            @Override
            public ArrayList<LeaderboardData> getLeaderboardData() {
                if(gameController != null){
                    return gameController.getLeaderboardData();
                }

                return null;
            }
        });

    }

    public BufferStrategy getGamePanelCanvasBufferStrategy() {
        return gamePanel.getCanvasBufferStrategy();
    }
}