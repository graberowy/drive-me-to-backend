package drive_me_to.car.web.controller;

import drive_me_to.car.repository.Car;
import drive_me_to.car.service.CarServiceBasic;
import drive_me_to.car.web.resources.CarMapperImpl;
import drive_me_to.data.enums.CarMake;
import drive_me_to.data.enums.CarStatus;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
@ContextConfiguration(classes = {CarMapperImpl.class, CarController.class})
class CarControllerTest {

    @MockBean
    private CarServiceBasic carServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_post_request_which_has_no_existing_car_then_car_should_be_returned_as_response() throws Exception {
        //given
        Car car = new Car();
        car.setMake(CarMake.BMW);
        car.setStatus(CarStatus.READY_TO_GO);
        car.setCurrency("PLN");
        car.setPricePerKm(BigDecimal.valueOf(2));
        car.setId(1L);

        Mockito.when(carServiceBasic.save(any())).thenReturn(car);
        //when
        //then
        mockMvc.perform(post("/api/v1/cars")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"make\": \"BMW\",\n" +
                                "  \"status\": \"READY_TO_GO\",\n" +
                                "  \"currency\": \"PLN\",\n" +
                                "  \"pricePerKm\": 2\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.make", equalTo("BMW")))
                .andExpect(jsonPath("$.status", equalTo("READY_TO_GO")))
                .andExpect(jsonPath("$.currency", equalTo("PLN")))
                .andExpect(jsonPath("$.pricePerKm", equalTo(2)))
                .andExpect(jsonPath("$.driver").isEmpty())
                .andExpect(jsonPath("$.currentLocation").doesNotExist());
    }
}