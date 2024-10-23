package controller;

import GUI.WindowBlackJack;
import model.Card;
import model.Dealer;
import model.Player;

import java.util.*;

public class Game {

    private final List<Player> players = new ArrayList<>();

    private Dealer dealer;

    private ArrayList<Card> deck;

    private int maxNumberOfPlayers = 7;

    private final Random random = new Random();

    public Game(int numberOfPlayer) {
        for(int i =0; i<numberOfPlayer; i++){
            players.add(new Player());
        }
        System.out.println("Number of players: " + players.size());

        startGame();

        new WindowBlackJack(dealer, players.getFirst(), deck, this);
    }

    public void startGame(){
        //deck
        this.deck = this.buildDeck();
        this.shuffledDeck();

        //dealer
        this.dealer = new Dealer(deck.removeLast());

        //players
        giveFirstCards();

        System.out.println("DEALER: ");
        System.out.println("score: "+dealer.getScore());
        System.out.println("ace count: "+dealer.getAceCount());

        for(Player player : players){
            System.out.println("PLAYER: ");
            System.out.println("score: "+player.getScore());
            System.out.println("ace count: "+player.getAceCount());

            player.bet(10);
        }
    }

    public void shuffledDeck(){
        Collections.shuffle(deck);

//        possible algo
//        for (int i = 0; i < deck.size(); i++) {
//            int j = random.nextInt(deck.size());
//            Card currCard = deck.get(i);
//            Card randomCard = deck.get(j);
//            deck.set(i, randomCard);
//            deck.set(j, currCard);
//        }

        System.out.println("AFTER SHUFFLE: ");
        System.out.println(deck);
    }

    public ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        Arrays.stream(types).forEach(type ->Arrays.stream(values).forEach(value ->deck.add(new Card(type,value))));

        System.out.println("BUILD DECK:");
        System.out.println(deck);
        return deck;
    }

    public void giveFirstCards(){

        giveCardsToAllPlayers();

        //give dealer faced card
        dealer.addACard(deck.removeLast());

        giveCardsToAllPlayers();
    }

    public void giveCardsToAllPlayers(){
        players.forEach(player -> player.addACard(deck.removeLast()));
    }

}
