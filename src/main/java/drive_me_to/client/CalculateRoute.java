package drive_me_to.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.List;

@Getter
@Setter
@ToString
public class CalculateRoute {
    @JsonProperty("routes")
    private List<Route> routes;

    @Getter
    @Setter
    @ToString
    public static class Route {
        @JsonProperty("sections")
        private List<Section> sections;
    }

    @Getter
    @Setter
    @ToString
    public static class Section {
        @JsonProperty("summary")
        private Summary summary;
        @JsonProperty("transport")
        private Transport transport;
    }

    @Getter
    @Setter
    @ToString
    public static class Summary {
        @JsonProperty("length")
        private Long length;
    }

    @Getter
    @Setter
    @ToString
    public static class Transport {
        @JsonProperty("mode")
        private String mode;
    }


}
