package com.example.funbanners.ControllerTests;


import com.example.funbanners.controller.FilesController;
//import com.example.funbanners.service.FilesDiskStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FilesController.class)
public class FilesControllerTests {

    /** This class should be tested when the service
     * works properly. */


    ////////////////////
    ////// SET UP //////
    ////////////////////

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

//    @MockBean
//    FilesDiskStorage diskStorageService;

    @Before
    public void setUp() {
        setUpFilesControllerMock();
    }

    /////////////////////
    //////  TESTS  //////
    /////////////////////



    /////////////////////////////
    ////// CONTROLLER MOCK //////
    /////////////////////////////

    private void setUpFilesControllerMock() {




    }


}
