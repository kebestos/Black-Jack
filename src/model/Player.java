package model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Card> cards;

    private int money;

    private int currentMise;

    public Player() {
        this.cards = new ArrayList<>();
        this.money = 100;
        this.currentMise = 0;
    }

    public List<Card> getCards(){
        return cards;
    }

    public int getScore(){
        System.out.println("cards: " + cards);
        return cards.stream().mapToInt(Card::getValue).sum();
    }

    public int getScoreWithAceReduce(){
        int scoreReduced = getScore();
        int aceCount = getAceCount();
        while (scoreReduced > 21 && aceCount > 0){
            scoreReduced -= 10;
            aceCount -= 1;
        }
        System.out.println("score reduced: " + scoreReduced);
        return scoreReduced;
    }

    public void addACard(Card card){
        cards.add(card);
    }

    public int getAceCount(){
        int count = 0;
        for(Card card : cards){
            if(card.isAce()) {
                count++;
            }
        }
        return count;
    }

    public Boolean asABlackJack(){
        return cards.size() == 2 && getScore() == 21;
    }

    public void bet(int moneyToBet){
        if(this.money > 0 && this.money-moneyToBet > 0){
            currentMise += moneyToBet;
            money-=moneyToBet;
        }
    }

    public void betWasWin(){
        money += currentMise*2;
        currentMise = 0;
        System.out.println("money player: " + money);
    }

    //stop

    //double down

    //surrender

}
