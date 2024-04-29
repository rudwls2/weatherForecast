package com.example.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/save-forecast")
    public ResponseEntity<String> saveWeatherForecastToDB() {
        try {
            weatherService.saveWeatherForecastToDB();
            return ResponseEntity.ok("날씨 정보를 성공적으로 저장했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("날씨 정보 저장에 실패하였습니다.");
        }
    }

    @GetMapping("/get-forecast")
    public ResponseEntity<?> getWeatherForecastFromDB() {
        try {
            return weatherService.getWeatherForecastFromDB();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("날씨 정보 조회에 실패했습니다.");
        }
    }
    
    
}
