package drive_me_to.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class HEREMapsAPI {
    private final RestTemplate restTemplate;
    private final String calculateRouteUrl;
    private final String geoCodeUrl;
    private final String apiKey;

    public HEREMapsAPI(RestTemplate restTemplate, @Value("${calculateroute.url}") String calculateRouteUrl, @Value("${geocode.url}") String geoCodeUrl, @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.calculateRouteUrl = calculateRouteUrl;
        this.geoCodeUrl = geoCodeUrl;
        this.apiKey = apiKey;
    }

    public CalculateRoute getRoute(String departureAddress, String destinationAddress, String routeMode) {
        if (departureAddress != null && destinationAddress != null && routeMode != null) {
            GeoCode.Coordinates departure = getLocalization(departureAddress).getLocation().get(0).getCoordinates();
            GeoCode.Coordinates destination = getLocalization(destinationAddress).getLocation().get(0).getCoordinates();
            String url = calculateRouteUrl + "?transportMode=car&origin=" + departure.getLat() + "," + departure.getLng() + "&destination=" + destination.getLat() + "," + destination.getLng() + "&routingMode=" + routeMode + "&units=metric&apikey=" + apiKey + "&return=summary";
            ResponseEntity<CalculateRoute> response = restTemplate.getForEntity(url, CalculateRoute.class);
            return response.getBody();
        } else throw new IllegalArgumentException("all arguments required");
    }

    public GeoCode getLocalization(String address) {
        if(address != null) {
            String url = geoCodeUrl + "?q=" + address + "&apikey=" + apiKey;
            ResponseEntity<GeoCode> response = restTemplate.getForEntity(url, GeoCode.class);
            return response.getBody();
        } else throw new IllegalArgumentException("address required");
    }


}

