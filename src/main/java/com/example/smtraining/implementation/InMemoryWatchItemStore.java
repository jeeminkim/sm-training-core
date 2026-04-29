package com.example.smtraining.implementation;

import com.example.smtraining.contract.WatchItemStore;
import com.example.smtraining.dto.WatchItemDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryWatchItemStore
 *
 * WatchItemStore Interface를 실제로 구현한 클래스.
 *
 * 웹 버전에서는 Spring이 이 클래스를 Bean으로 관리할 수 있도록
 * @Repository를 붙인다.
 *
 * 회사 시스템에서 DSC 또는 DAO 구현체와 비슷한 역할이다.
 */
@Repository
public class InMemoryWatchItemStore implements WatchItemStore {

    private final List<WatchItemDto> items = new ArrayList<WatchItemDto>();

    public InMemoryWatchItemStore() {
        items.add(new WatchItemDto("KRW-BTC", "비트코인", "Y"));
        items.add(new WatchItemDto("KRW-ETH", "이더리움", "Y"));
    }

    public List<WatchItemDto> selectList(WatchItemDto condition) {
        List<WatchItemDto> result = new ArrayList<WatchItemDto>();

        for (WatchItemDto item : items) {
            if (condition == null || isEmpty(condition.getMarketName())) {
                result.add(item);
            } else if (item.getMarketName() != null && item.getMarketName().contains(condition.getMarketName())) {
                result.add(item);
            }
        }

        return result;
    }

    public WatchItemDto selectDetail(String marketCode) {
        for (WatchItemDto item : items) {
            if (item.getMarketCode().equals(marketCode)) {
                return item;
            }
        }

        return null;
    }

    public int insertOne(WatchItemDto item) {
        items.add(item);
        return 1;
    }

    public int deleteAll() {
        int count = items.size();
        items.clear();
        return count;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}