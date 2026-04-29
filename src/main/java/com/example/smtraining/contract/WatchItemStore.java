package com.example.smtraining.contract;

import com.example.smtraining.dto.WatchItemDto;

import java.util.List;

public interface WatchItemStore {

    List<WatchItemDto> selectList(WatchItemDto condition);

    WatchItemDto selectDetail(String marketCode);

    int insertOne(WatchItemDto item);

    int deleteAll();
}