package notetaking;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class NoteControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createANewNote() throws Exception {
        String noteJson = "{\"body\":\"Example Note\"}";
        this.mockMvc.perform(post("/api/notes")
                .contentType(contentType)
                .content(noteJson))
                .andExpect(status().isOk());
    }

    @Test
    public void getAnExistingNote() throws Exception {
        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOfMyNotes() throws Exception {
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/notes?query=forget"))
                .andExpect(status().isOk());
    }


}
