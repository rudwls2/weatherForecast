package com.example.weather.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.WeatherEntity;
import com.example.weather.repository.WeatherRepository;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final RestTemplate restTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public void saveWeatherForecastToDB() {
        try {
            // 외부 API 호출하여 날씨 정보 가져오기
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + "qbjscquZ%2BgfjQ2TgP7cpB2d%2FaN0mcOdC8qAAaLyvbJZgeTKKm3ZNH%2BfEaVKex0Wylm11n64XlnletiP2WZiomQ%3D%3D"); // API 인증키 설정
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<WeatherDto> responseEntity = restTemplate.exchange(
                    "https://api.example.com/weather", HttpMethod.GET, requestEntity, WeatherDto.class);

            // 가져온 날씨 정보를 DB에 저장
            WeatherDto weatherDto = responseEntity.getBody();
            WeatherEntity weatherEntity = convertDtoToEntity(weatherDto);
            weatherRepository.save(weatherEntity);
        } catch (Exception e) {
            // 예외 처리
            logger.error("날씨정보 저장에 실패하였습니다.", e);
            throw new RuntimeException("날씨정보 저장에 실패하였습니다.", e);
        }
    }
    
    public ResponseEntity<List<WeatherEntity>> getWeatherForecastFromDB() {
        try {
            // DB에서 날씨 정보 조회
            List<WeatherEntity> weatherList = weatherRepository.findAll();
            if (weatherList.isEmpty()) {
                return ResponseEntity.noContent().build();// Http status 204 오류반환
            }
            return ResponseEntity.ok(weatherList);
        } catch (Exception e) {
            // 예외 처리
            logger.error("날씨정보 조회에 실패하였습니다.", e);
            throw new RuntimeException("날씨정보 조회에 실패하였습니다.", e);
        }
    }


    private WeatherEntity convertDtoToEntity(WeatherDto weatherDto) {
    	
        WeatherEntity weatherEntity = new WeatherEntity();
        
        weatherEntity.setBaseDate(weatherDto.getBaseDate());
        weatherEntity.setBaseTime(weatherDto.getBaseTime());
        weatherEntity.setCategory(weatherDto.getCategory());
        weatherEntity.setFcstDate(weatherDto.getFcstDate());
        weatherEntity.setFcstTime(weatherDto.getFcstTime());
        weatherEntity.setFcstValue(weatherDto.getFcstValue());
        weatherEntity.setNx(weatherDto.getNx());
        weatherEntity.setNy(weatherDto.getNy());
        
        return weatherEntity;
    }
}
