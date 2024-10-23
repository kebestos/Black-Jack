package GUI;

import model.Card;
import model.Dealer;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import controller.Game;

public class WindowBlackJack {

    Game blackJack;
    Dealer dealer;
    Player player;
    ArrayList<Card> deck;

    int boardWith = 600;
    int boardHeight = 600;

    int cardWith = 110; //ratio 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try{
                drawHiddenCardDealerHand(g);

                drawDealerHand(g);

                drawPlayerHand(g);

                if(!stayButton.isEnabled()){
                    int dealerFinalScore = dealer.getScoreWithAceReduce();
                    int playerFinalScore = player.getScoreWithAceReduce();
                    System.out.println("STAY: dealer score: "+dealerFinalScore+" player score: "+ playerFinalScore);

                    String message = "";
                    if(playerFinalScore > 21){
                        message = "You lose !";
                    }
                    else if(dealerFinalScore > 21){
                        message = "You win !";
                        player.betWasWin();
                    }
                    //both you and dealer <= 21
                    else if (dealerFinalScore == playerFinalScore){
                        message = "Tie !";
                    }
                    else if (dealerFinalScore < playerFinalScore){
                        message = "You win !";
                        player.betWasWin();
                    }
                    else {
                        message = "You Lose !";
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString(message, 220, 250);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private void drawHiddenCardDealerHand(Graphics g) {
            Image hiddenCardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("../cards/BACK.png"))).getImage();
            if(!stayButton.isEnabled()){
                hiddenCardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(dealer.getHiddenCard().getImagePath()))).getImage();
            }
            g.drawImage(hiddenCardImg, 20, 20, cardWith, cardHeight, null);
        }

        private void drawDealerHand(Graphics g) {
            for(int i=0;i<dealer.getCards().size();i++){
                Card card = dealer.getCards().get(i);
                Image dealerImgCard = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
                g.drawImage(dealerImgCard, cardWith + 25 + (cardWith + 5)*i, 20, cardWith, cardHeight, null);
            }
        }

        private void drawPlayerHand(Graphics g) {
            for(int i=0;i<player.getCards().size();i++){
                Card card = player.getCards().get(i);
                Image playerImgCard = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
                g.drawImage(playerImgCard, 20 + (cardWith + 5)*i, 320, cardWith, cardHeight, null);
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton rePlayButton = new JButton("Replay");


    public WindowBlackJack(Dealer dealer, Player player, ArrayList<Card> deck, Game game){
        //refacto constructor acces only Game
        this.dealer = dealer;
        this.player = player;
        this.deck = deck;
        this.blackJack = game;

        frame.setVisible(true);
        frame.setSize(boardWith,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53,101,77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = deck.removeLast();
                player.addACard(card);
                System.out.println("cards: " + player.getCards());
                if(player.getScoreWithAceReduce() > 21){
                    hitButton.setEnabled(false);
                }
                gamePanel.repaint();
            }
        });
        buttonPanel.add(hitButton);

        stayButton.setFocusable(false);
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while(dealer.getScoreWithAceReduce() < 17){
                    Card card = deck.removeLast();
                    dealer.addACard(card);
                }

                gamePanel.repaint();
            }
        });
        buttonPanel.add(stayButton);

        rePlayButton.setFocusable(false);
        rePlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                blackJack.startGame();
                blackJack = new Game(1);
                frame.dispose();

                gamePanel.repaint();
            }
        });
        buttonPanel.add(rePlayButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        if(player.asABlackJack()){
            stayButton.doClick();
        }

        gamePanel.repaint();
    }
}
