package basket.watch.backend.scraper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.io.ResourceLoader;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@UtilityClass
public class PlatformUtils {

    private static final String PLATFORMS_JSON_RESOURCE = "static/platforms.json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<String, Platform> parsePlatforms(ResourceLoader resourceLoader) {
        Map<String, Platform> domainPlatform = new HashMap<>();
        Optional<InputStream> json = resourceLoader.getResourceAsStream(PLATFORMS_JSON_RESOURCE);
        if (json.isPresent()) {
            try {
                List<Platform> platforms = OBJECT_MAPPER.readValue(json.get(), new TypeReference<>() {
                });
                for (Platform platform : platforms) {
                    domainPlatform.put(platform.getDomain(), platform);
                }
            } catch (IOException e) {
                log.error("failed to process platforms json {}", e.getMessage());
            }
        }
        return domainPlatform;
    }
}
