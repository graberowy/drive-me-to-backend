package drive_me_to.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GeoCode {
    @JsonProperty("items")
    private List<Position> location;

    @Getter
    @Setter
    @ToString
    public static class Position {
        @JsonProperty("title")
        private String addressDetails;
        @JsonProperty("position")
        private Coordinates coordinates;
    }

    @Getter
    @Setter
    @ToString
    public static class Coordinates {
        private Float lat;
        private Float lng;
    }


}
