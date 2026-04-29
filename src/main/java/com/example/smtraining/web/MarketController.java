package com.example.smtraining.web;

import com.example.smtraining.dto.WatchItemDto;
import com.example.smtraining.process.WatchItemProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MarketController
 *
 * 웹 요청의 진입점.
 *
 * 회사 시스템에서 Controller, Action, Handler와 비슷한 역할이다.
 *
 * 화면 JSP의 JavaScript operation이 fetch로 이 Controller를 호출한다.
 *
 * 흐름:
 * MarketView.jsp
 *   → fetch("/market/selectList.do")
 *   → MarketController.selectList()
 *   → WatchItemProcessService.selectList()
 *   → WatchItemStore Interface
 *   → InMemoryWatchItemStore 구현체
 */
@Controller
@RequestMapping("/market")
public class MarketController {

    private final WatchItemProcessService watchItemProcessService;

    @Autowired
    public MarketController(WatchItemProcessService watchItemProcessService) {
        this.watchItemProcessService = watchItemProcessService;
    }

    /**
     * 화면 진입.
     *
     * 브라우저에서 /market/view.do 로 접근하면
     * /WEB-INF/views/MarketView.jsp 를 보여준다.
     */
    @RequestMapping(value = "/view.do", method = RequestMethod.GET)
    public String view() {
        return "MarketView";
    }

    /**
     * 목록 조회.
     *
     * JSP operation 기준:
     * searchMarketList 또는 selectList 역할.
     */
    @RequestMapping(value = "/selectList.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectList(@ModelAttribute WatchItemDto condition) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            List<WatchItemDto> list = watchItemProcessService.selectList(condition);

            result.put("success", true);
            result.put("data", list);
            result.put("count", list.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    /**
     * 상세 조회.
     *
     * JSP operation 기준:
     * selectDetail 역할.
     */
    @RequestMapping(value = "/selectDetail.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectDetail(@RequestParam("marketCode") String marketCode) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            WatchItemDto detail = watchItemProcessService.selectDetail(marketCode);

            result.put("success", true);
            result.put("data", detail);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    /**
     * 단건 등록.
     *
     * JSP operation 기준:
     * insertOne 역할.
     */
    @RequestMapping(value = "/insertOne.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertOne(@ModelAttribute WatchItemDto item) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            int count = watchItemProcessService.insertOne(item);

            result.put("success", true);
            result.put("count", count);
            result.put("message", "관심 항목이 등록되었습니다.");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    /**
     * 전체 삭제.
     *
     * JSP operation 기준:
     * deleteAll 역할.
     */
    @RequestMapping(value = "/deleteAll.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteAll() {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            int count = watchItemProcessService.deleteAll();

            result.put("success", true);
            result.put("count", count);
            result.put("message", "전체 삭제되었습니다.");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
}