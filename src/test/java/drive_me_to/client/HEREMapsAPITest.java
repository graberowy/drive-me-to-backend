package drive_me_to.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HEREMapsAPITest {
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final HEREMapsAPI hereMapsAPI = new HEREMapsAPI(restTemplate,
            "https://router.hereapi.com/v8/routes",
            "https://geocode.search.hereapi.com/v1/geocode",
            "wEURuNl2SjvSarjNLCt2kXZkYMEQ8sfjYPvDWndVVbs");

    @Test
    void when_use_getLocalization_then_geocode_object_should_be_returned() {
        //given
        GeoCode.Coordinates coordinates = new GeoCode.Coordinates();
        coordinates.setLat((float) 51.77857);
        coordinates.setLng((float) 19.45245);

        GeoCode.Position position = new GeoCode.Position();
        position.setAddressDetails("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        position.setCoordinates(coordinates);

        GeoCode geoCode = new GeoCode();
        geoCode.setLocation(List.of(position));

        Mockito.when(restTemplate.getForEntity("https://geocode.search.hereapi.com/v1/geocode?q=ogrodowa8lodz&apikey=wEURuNl2SjvSarjNLCt2kXZkYMEQ8sfjYPvDWndVVbs",
                        GeoCode.class))
                .thenReturn(new ResponseEntity(geoCode, HttpStatus.OK));
        //when
        GeoCode geoCodeResult = hereMapsAPI.getLocalization("ogrodowa8lodz");
        //then
        assertEquals(1, geoCodeResult.getLocation().size());
        assertEquals(position.getAddressDetails(), geoCodeResult.getLocation().get(0).getAddressDetails());
        assertEquals(coordinates, geoCodeResult.getLocation().get(0).getCoordinates());

    }

    @Test
    void when_use_getLocalization_with_null_param_then_exception_should_be_thrown(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            hereMapsAPI.getLocalization(null);
        }, "address required");
    }

    @Test
    void when_use_getRoute_then_calculateRoute_object_should_be_returned() {
        //given
        GeoCode.Coordinates coordinatesDeparture = new GeoCode.Coordinates();
        coordinatesDeparture.setLat((float) 51.77857);
        coordinatesDeparture.setLng((float) 19.45245);

        GeoCode.Position positionDeparture = new GeoCode.Position();
        positionDeparture.setAddressDetails("ulica Ogrodowa 8, 91-062 Łódź, Polska");
        positionDeparture.setCoordinates(coordinatesDeparture);

        GeoCode geoCodeDeparture = new GeoCode();
        geoCodeDeparture.setLocation(List.of(positionDeparture));

        GeoCode.Coordinates coordinatesDestination = new GeoCode.Coordinates();
        coordinatesDestination.setLat((float) 51.74482);
        coordinatesDestination.setLng((float) 19.40752);

        GeoCode.Position positionDestination = new GeoCode.Position();
        positionDestination.setAddressDetails("ulica Przełajowa 9, 94-045 Łódź, Polska");
        positionDestination.setCoordinates(coordinatesDestination);

        GeoCode geoCodeDestination = new GeoCode();
        geoCodeDestination.setLocation(List.of(positionDestination));

        CalculateRoute.Summary summary = new CalculateRoute.Summary();
        summary.setLength(8119L);

        CalculateRoute.Transport transport = new CalculateRoute.Transport();
        transport.setMode("car");

        CalculateRoute.Section section = new CalculateRoute.Section();
        section.setTransport(transport);
        section.setSummary(summary);

        CalculateRoute.Route route = new CalculateRoute.Route();
        route.setSections(List.of(section));

        CalculateRoute calculateRoute = new CalculateRoute();
        calculateRoute.setRoutes(List.of(route));

        Mockito.when(restTemplate.getForEntity("https://geocode.search.hereapi.com/v1/geocode?q=ogrodowa8lodz&apikey=wEURuNl2SjvSarjNLCt2kXZkYMEQ8sfjYPvDWndVVbs",
                        GeoCode.class))
                .thenReturn(new ResponseEntity(geoCodeDeparture, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity("https://geocode.search.hereapi.com/v1/geocode?q=przelajowa9lodz&apikey=wEURuNl2SjvSarjNLCt2kXZkYMEQ8sfjYPvDWndVVbs",
                        GeoCode.class))
                .thenReturn(new ResponseEntity(geoCodeDestination, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity("https://router.hereapi.com/v8/routes?transportMode=car&origin=51.77857,19.45245&destination=51.74482,19.40752&routingMode=fast&units=metric&apikey=wEURuNl2SjvSarjNLCt2kXZkYMEQ8sfjYPvDWndVVbs&return=summary",
                        CalculateRoute.class))
                .thenReturn(new ResponseEntity(calculateRoute, HttpStatus.OK));
        //when
        CalculateRoute calculateRouteResult = hereMapsAPI.getRoute("ogrodowa8lodz",
                "przelajowa9lodz",
                "fast");
        //then
        assertEquals(1, calculateRouteResult.getRoutes().size());
        assertEquals(1, calculateRouteResult.getRoutes().get(0).getSections().size());
        assertEquals(summary, calculateRouteResult.getRoutes().get(0).getSections().get(0).getSummary());
        assertEquals(transport, calculateRouteResult.getRoutes().get(0).getSections().get(0).getTransport());
    }

    @Test
    void when_use_getRoute_with_null_param_then_exception_should_be_thrown(){
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            hereMapsAPI.getRoute(null, null, "fast");
        }, "all arguments required");
    }

}