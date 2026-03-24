package com.example.demo2.controller;

import com.example.demo2.service.ScraperService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class CalculatorController2 {

    private final ScraperService scraperService;

    public CalculatorController2(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/api/table")
    public Map<String, Object> getRewardsJson() {
        JsonNode data = scraperService.getAllRewards();
        if (data == null) return Map.of("coins", List.of());

        // --- ті самі часи ---
        Map<String, Double> customTimes = new HashMap<>();
        customTimes.put("rlt", 600.0);
        customTimes.put("btc", 600.0);
        customTimes.put("eth", 600.0);
        customTimes.put("bnb", 600.0);
        customTimes.put("pol", 600.0);
        customTimes.put("sol", 600.0);
        customTimes.put("doge", 600.0);
        customTimes.put("ltc", 600.0);
        customTimes.put("trx", 600.0);
        customTimes.put("xrp", 600.0);
        customTimes.put("usdt", 600.0);
        customTimes.put("rst", 600.0);
        customTimes.put("hmt", 600.0);
        customTimes.put("algo", 600.0);

        List<Map<String, Object>> coinsList = new ArrayList<>();

        Iterator<Map.Entry<String, JsonNode>> coins = data.fields();
        while (coins.hasNext()) {
            Map.Entry<String, JsonNode> coinEntry = coins.next();
            String code = coinEntry.getKey().toLowerCase();

            Map<String, Object> coinObj = new HashMap<>();
            coinObj.put("code", code);
            coinObj.put("time", customTimes.getOrDefault(code, 600.0));

            // 👇 ТУТ ГОЛОВНЕ — повністю транзитом всі ліги
            Map<String, Object> rewards = new HashMap<>();

            Iterator<Map.Entry<String, JsonNode>> levels = coinEntry.getValue().fields();
            while (levels.hasNext()) {
                Map.Entry<String, JsonNode> level = levels.next();

                // нічого не фільтруємо
                rewards.put(level.getKey(), level.getValue().asDouble());
            }

            coinObj.put("rewards", rewards);
            coinsList.add(coinObj);
        }

        return Map.of("coins", coinsList);
    }
}
