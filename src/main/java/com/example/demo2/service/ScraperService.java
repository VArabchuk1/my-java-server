package com.example.demo2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScraperService {

    public JsonNode getAllRewards() {
        try {
            String url = "https://minaryganar.com/calculator/";
            String html = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .get()
                    .html();

            Pattern pattern = Pattern.compile("const coinMetaValues\\s*=\\s*(\\{.*?\\});", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            if (matcher.find()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(matcher.group(1));
            }
        } catch (Exception e) {
            System.err.println("Помилка отримання нагород: " + e.getMessage());
        }
        return null;
    }
}
