package com.cabAggregator.DTO;

import org.springframework.data.mongodb.core.geo.GeoJson;

public record getLocationDTO(GeoJson geoJson) {
}
