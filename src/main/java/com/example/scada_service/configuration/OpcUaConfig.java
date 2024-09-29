package com.example.scada_service.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class OpcUaConfig {
    private List<String> tags;

    @PostConstruct
    public void loadTagsFromFile() {
        try (InputStream inputStream = new ClassPathResource("tags.yml").getInputStream()) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            tags = (List<String>) data.get("tags");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке тегов из файла tags.yml", e);
        }
    }

    public List<String> getTags() {
        return tags;
    }
}
