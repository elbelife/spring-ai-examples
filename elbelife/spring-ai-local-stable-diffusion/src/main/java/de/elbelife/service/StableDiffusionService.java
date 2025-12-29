
package de.elbelife.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StableDiffusionService {

    private static final String SD_API = "http://127.0.0.1:7860/sdapi/v1/txt2img";

    public Map<String, Object> generate(String prompt) {
        RestTemplate rest = new RestTemplate();
        List<String> styles = new ArrayList<>(List.of("cyberpunk", "anime", "oil painting", "watercolor"));
        Iterator<String> iterator = styles.iterator();
        while (iterator.hasNext()) {
            String style = iterator.next();
            if (style.equals("cyberpunk")) {
                iterator.remove();
            }
        }
        Map<String, Object> result = new HashMap<>();

        for (String style : styles) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("prompt", prompt + ", style: " + style);
            payload.put("steps", 20);
            payload.put("width", 512);
            payload.put("height", 512);
            payload.put("sampler_name", "Euler a");
            payload.put("cfg_scale", 7);

            Map resp = rest.postForObject(SD_API, payload, Map.class);
            result.put(style, resp.get("images"));
        }
        return result;
    }
}
