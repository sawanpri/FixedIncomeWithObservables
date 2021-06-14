package com.jeff.domain;

import com.github.underscore.lodash.U;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.*;

public class Subscriber{
    List<Trade> tradeList = new LinkedList<Trade>();

    private String name;

    Consumer<List<Trade>> tradeConsumer= list -> {
        tradeList.addAll(list);
        getLargestTradeBySize();
        getAveragePriceForSymbol();
        getTradesByGroup("status","X");
    };

    public Subscriber(Observable publisher, String name) {
        this.name=name;
        System.out.println("Subscriber registered" + name);
        publisher.subscribe(tradeConsumer);
    }


    public void getAveragePriceForSymbol(){
        System.out.println("Printing Average price");

        Observable.fromIterable(this.tradeList)
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(Observable::toList)
                .map(tradesGroup -> {
                    Map<String, Double> tradeMap=new HashMap<String, Double>();
                    tradeMap.put(tradesGroup.get(0).getSymbol(), U.average(tradesGroup,trade -> trade.getPrice()));
                    return tradeMap;
                })
                .subscribe(System.out::println);
}

    public void getLargestTradeBySize(){
        System.out.println("Printing Largest trade by size");

        Observable.fromIterable(this.tradeList)
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(Observable::toList)
                .map(tradesGroup -> tradesGroup.stream().max(Comparator.comparing(Trade::getSize)))
                .subscribe(trade -> System.out.println(trade.get().symbol +" "+ trade.get().toString()));
    }

    public void getTradesByGroup(String column, String value){
        System.out.println("Printing Trades by group");

        Observable.fromIterable(this.tradeList)
                .filter(trade -> U.pluck(Arrays.asList(trade),column).get(0).equals(value))
                .groupBy(trades -> trades.symbol)
                .flatMapSingle(Observable::toList)
                .subscribe(System.out::println);

        HashMap<String, List<Trade>> tradeMap = new HashMap<>();


        tradeList.stream().forEach(trade -> {
            if (tradeMap.containsKey(trade.symbol)) {
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradeMap.get(trade.symbol).add(trade);
                    }
                }
            } else {
                List<Trade> list = new ArrayList<>();
                list.add(trade);
                if (column.equals("price")) {
                    if (trade.price.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else if (column.equals("size")) {
                    if (trade.size == Integer.valueOf(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else if (column.equals("status")) {
                    if (trade.status.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }
                } else {
                    if (trade.timestamp.equals(value)) {
                        tradeMap.put(trade.symbol, list);
                    }

                }
            }
        });
//        return tradeMap;
        System.out.println(tradeMap);
    }


}
