package drive_me_to.driver.web.controller;

import drive_me_to.car.repository.Car;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
import drive_me_to.data.enums.Languages;
import drive_me_to.driver.repository.Driver;
import drive_me_to.driver.service.DriverServiceBasic;
import drive_me_to.driver.web.resources.DriverMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriverController.class)
@ContextConfiguration(classes = {DriverMapperImpl.class, DriverController.class})
class DriverControllerTest {

    @MockBean
    private DriverServiceBasic driverServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_post_request_which_has_no_existing_driver_then_driver_should_be_returned_as_response() throws Exception {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));
        car.setId(1L);

        Driver driver = new Driver();
        driver.setName("Sebastian Nowak");
        driver.setLanguages(Set.of(Languages.POLISH));
        driver.setId(1L);
        driver.setCar(car);

        Mockito.when(driverServiceBasic.save(any())).thenReturn(driver);
        //when
        //then
        mockMvc.perform(post("/api/v1/drivers")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Sebastian Nowak\",\n" +
                                "  \"languages\": [\"POLISH\"],\n" +
                                "  \"car\": { \"id\": 1, \"make\": \"BMW\", \"status\": \"READY_TO_GO\", \"currency\": \"PLN\", \"pricePerKm\": 2 }\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Sebastian Nowak")))
                .andExpect(jsonPath("$.languages", equalTo(List.of("POLISH"))))
                .andExpect(jsonPath("$.car.id", equalTo(1)))
                .andExpect(jsonPath("$.car.make", equalTo("BMW")))
                .andExpect(jsonPath("$.car.status", equalTo("READY_TO_GO")))
                .andExpect(jsonPath("$.car.currency", equalTo("PLN")))
                .andExpect(jsonPath("$.car.pricePerKm", equalTo(2)));
    }

}