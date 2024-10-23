package model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Dealer extends Player {

    private final Card hiddenCard;

    public Dealer(Card hiddenCard){
        this.hiddenCard = hiddenCard;
        this.cards = new ArrayList<>();
    }

    public Card getHiddenCard(){
    return hiddenCard;
    }

    @Override
    public int getScore(){

        System.out.println("hidden card: "+hiddenCard);
        System.out.println("cards: " + cards);

        return this.cards.stream().mapToInt(Card::getValue).sum() + hiddenCard.getValue();
    }

    @Override
    public int getAceCount(){
        AtomicInteger aceCount = new AtomicInteger();
        if (hiddenCard.isAce()) aceCount.getAndIncrement();
        this.cards.forEach(card -> {if (card.isAce()) aceCount.getAndIncrement();});
        return aceCount.get();
    }
}
