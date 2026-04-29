package com.example.smtraining.dto;

public class WatchItemDto {

    private String marketCode;
    private String marketName;
    private String useYn;

    public WatchItemDto() {
    }

    public WatchItemDto(String marketCode, String marketName, String useYn) {
        this.marketCode = marketCode;
        this.marketName = marketName;
        this.useYn = useYn;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String toString() {
        return "WatchItemDto{" +
                "marketCode='" + marketCode + '\'' +
                ", marketName='" + marketName + '\'' +
                ", useYn='" + useYn + '\'' +
                '}';
    }
}