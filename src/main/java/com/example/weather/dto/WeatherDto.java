package com.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherDto {
	
	@JsonProperty("baseDate")
	private String baseDate;
	
	@JsonProperty("baseTime")
	private String baseTime;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("fcstDate")
	private String fcstDate;
	
	@JsonProperty("fcstTime")
	private String fcstTime;
	
	@JsonProperty("fcstValue")
	private String fcstValue;
	
	@JsonProperty("nx")
	private Long nx;
	
	@JsonProperty("ny")
	private Long ny;


}
