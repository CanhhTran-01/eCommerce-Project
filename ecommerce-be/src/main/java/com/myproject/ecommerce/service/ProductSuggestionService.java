package com.myproject.ecommerce.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSuggestionService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY = "search:suggestions";

    // insert keyword suggestion
    public void insertTextForSuggestion(String text) {
        stringRedisTemplate.opsForZSet().incrementScore(KEY, text.toLowerCase(), 1);
    }

    public List<String> getSuggestionTexts(String keyword) {

        // get ZSET saved text (sort by score(popularity) high -> low)
        Set<String> texts = stringRedisTemplate.opsForZSet().reverseRange(KEY, 0, 100);

        if (texts == null) return List.of();

        return texts.stream()
                .filter(text -> text.startsWith(keyword.toLowerCase())) // filter texts corresponding keyword
                .limit(5) // limit 5 keyword with highest score (popularity)
                .toList();
    }
}
