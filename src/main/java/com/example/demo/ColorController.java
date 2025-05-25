package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ColorController {
    
    private final Map<String, List<String>> colorDatabase;
    private final List<ColorPalette> popularPalettes;
    
    public ColorController() {
        // Initialize color database
        colorDatabase = new HashMap<>();
        colorDatabase.put("red", Arrays.asList("#FF0000", "#FF5733", "#c21919", "#FF6347", "#FF4500"));
        colorDatabase.put("green", Arrays.asList("#00FF00", "#33FF73", "#C3FF00", "#228B22", "#008000"));
        // ... add all other colors
        
        // Initialize popular palettes
        popularPalettes = new ArrayList<>();
        popularPalettes.add(new ColorPalette("Sunset", 
            Arrays.asList("#FF7E5F", "#FEB47B", "#FF6B6B", "#FFA3A3", "#FFD3B6")));
        popularPalettes.add(new ColorPalette("Ocean",
            Arrays.asList("#0077BE", "#00A8E8", "#89CFF0", "#B0E0E6", "#ADD8E6")));
    }
    
    @GetMapping("/colors/{name}")
    public ResponseEntity<?> getColorsByName(@PathVariable String name) {
        String lowerName = name.toLowerCase();
        if (colorDatabase.containsKey(lowerName)) {
            return ResponseEntity.ok(colorDatabase.get(lowerName));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Color not found"));
        }
    }
    
    @GetMapping("/palette/random")
    public List<String> generateRandomPalette() {
        List<String> colorList = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < 21; i++) {
            String hexColor = String.format("#%06x", random.nextInt(0xffffff + 1));
            colorList.add(hexColor);
        }
        
        return colorList;
    }
    
    @GetMapping("/palettes/popular")
    public List<ColorPalette> getPopularPalettes() {
        return popularPalettes;
    }
}

// DTO for color palette
class ColorPalette {
    private String name;
    private List<String> colors;
    
    public ColorPalette(String name, List<String> colors) {
        this.name = name;
        this.colors = colors;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }
}