package com.example.smtraining.process;

import com.example.smtraining.contract.WatchItemStore;
import com.example.smtraining.dto.WatchItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WatchItemProcessService
 *
 * 업무 흐름을 담당하는 Process Service 계층.
 *
 * 웹 버전에서는 Spring이 이 클래스를 Bean으로 관리할 수 있도록
 * @Service를 붙인다.
 *
 * 회사 시스템에서 PSC, Service, BizService와 비슷한 역할이다.
 */
@Service
public class WatchItemProcessService {

    private final WatchItemStore watchItemStore;

    /**
     * 생성자 주입.
     *
     * 콘솔 버전:
     * - App.java에서 직접 new InMemoryWatchItemStore()를 넣는다.
     *
     * 웹 버전:
     * - Spring이 WatchItemStore 구현체를 찾아 자동으로 넣어준다.
     */
    @Autowired
    public WatchItemProcessService(WatchItemStore watchItemStore) {
        this.watchItemStore = watchItemStore;
    }

    public List<WatchItemDto> selectList(WatchItemDto condition) {
        return watchItemStore.selectList(condition);
    }

    public WatchItemDto selectDetail(String marketCode) {
        if (marketCode == null || marketCode.trim().length() == 0) {
            throw new IllegalArgumentException("marketCode는 필수입니다.");
        }

        return watchItemStore.selectDetail(marketCode);
    }

    public int insertOne(WatchItemDto item) {
        validateForInsert(item);
        return watchItemStore.insertOne(item);
    }

    public int deleteAll() {
        return watchItemStore.deleteAll();
    }

    private void validateForInsert(WatchItemDto item) {
        if (item == null) {
            throw new IllegalArgumentException("등록할 데이터가 없습니다.");
        }

        if (item.getMarketCode() == null || item.getMarketCode().trim().length() == 0) {
            throw new IllegalArgumentException("marketCode는 필수입니다.");
        }

        if (item.getMarketName() == null || item.getMarketName().trim().length() == 0) {
            throw new IllegalArgumentException("marketName은 필수입니다.");
        }
    }
}