package com.example.smtraining;

import com.example.smtraining.contract.WatchItemStore;
import com.example.smtraining.dto.WatchItemDto;
import com.example.smtraining.implementation.InMemoryWatchItemStore;
import com.example.smtraining.process.WatchItemProcessService;

import java.util.List;

public class App {

    public static void main(String[] args) {
        WatchItemStore store = new InMemoryWatchItemStore();
        WatchItemProcessService service = new WatchItemProcessService(store);

        System.out.println("=== onLoad / selectList ===");
        List<WatchItemDto> list = service.selectList(new WatchItemDto());
        printList(list);

        System.out.println("\n=== insertOne ===");
        service.insertOne(new WatchItemDto("KRW-XRP", "리플", "Y"));
        printList(service.selectList(new WatchItemDto()));

        System.out.println("\n=== selectDetail ===");
        WatchItemDto detail = service.selectDetail("KRW-BTC");
        System.out.println(detail);

        System.out.println("\n=== deleteAll ===");
        int deletedCount = service.deleteAll();
        System.out.println("deletedCount = " + deletedCount);
        printList(service.selectList(new WatchItemDto()));
    }

    private static void printList(List<WatchItemDto> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("(empty)");
            return;
        }

        for (WatchItemDto item : list) {
            System.out.println(item);
        }
    }
}