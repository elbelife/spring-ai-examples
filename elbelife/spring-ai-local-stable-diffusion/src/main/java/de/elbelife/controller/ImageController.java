
package de.elbelife.controller;

import de.elbelife.Utils.ImageUtils;
import de.elbelife.service.StableDiffusionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final StableDiffusionService service;

    public ImageController(StableDiffusionService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public Map<String, Object> generate(@RequestBody Map<String, String> body) throws IOException {
        Map<String, Object> res = service.generate(body.get("prompt"));
        for (Map.Entry<String, Object> entry : res.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            
            Object value = entry.getValue();
            if (value instanceof java.util.List) {
                java.util.List<String> images = (java.util.List<String>) value;
                for (String image : images) {
                    ImageUtils.saveBase64Image(image, entry.getKey(), "output");
                }
            } else if (value instanceof String) {
                ImageUtils.saveBase64Image((String) value,  entry.getKey() ,"output");
            }
        }
        return res;
    }
}
