
package de.elbelife.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StableDiffusionService {

    @Value("${SD_API}")
    private String sdApi;

    public Map<String, Object> generate(String prompt) {
        RestTemplate rest = new RestTemplate();
        List<String> styles = new ArrayList<>(List.of("cyberpunk", "anime", "oil painting", "watercolor"));
        Map<String, Object> result = new HashMap<>();

        for (String style : styles) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("prompt", prompt + ", style: " + style);
            payload.put("steps", 20);
            payload.put("width", 512);
            payload.put("height", 512);
            payload.put("sampler_name", "Euler a");
            payload.put("cfg_scale", 7);
            Map resp = rest.postForObject(sdApi, payload, Map.class);
            result.put(style, resp.get("images"));
        }
        return result;
    }
}
