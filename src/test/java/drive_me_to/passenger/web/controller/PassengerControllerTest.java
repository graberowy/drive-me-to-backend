package drive_me_to.passenger.web.controller;

import drive_me_to.passenger.repository.Passenger;
import drive_me_to.passenger.service.PassengerServiceBasic;
import drive_me_to.passenger.web.resources.PassengerMapperImpl;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PassengerController.class)
@ContextConfiguration(classes = {PassengerMapperImpl.class, PassengerController.class})
class PassengerControllerTest {

    @MockBean
    private PassengerServiceBasic passengerServiceBasic;

    @Autowired
    MockMvc mockMvc;

    @Test
    void when_send_post_request_which_has_no_existing_passenger_then_passenger_should_be_returned_as_response() throws Exception {
        //given
        Passenger passenger = new Passenger();
        passenger.setName("Jan Kowalski");
        passenger.setMobile("555666888");
        passenger.setId(1L);

        Mockito.when(passengerServiceBasic.save(any())).thenReturn(passenger);
        //when
        //then
        mockMvc.perform(post("/api/v1/passengers")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Jan Kowalski\",\n" +
                                "  \"mobile\": \"555666888\"\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Jan Kowalski")))
                .andExpect(jsonPath("$.mobile", equalTo("555666888")))
                .andExpect(jsonPath("$.trips").doesNotExist());
    }

}