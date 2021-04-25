package com.example.funbanners.controller;

import com.example.funbanners.service.CreateBinary;
import com.example.funbanners.service.Processor;
import com.example.funbanners.service.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;

@RestController
@RequestMapping("/pictures")
@CrossOrigin("http://localhost:3000")
public class FilesController {

    @Autowired
    Processor processor;

    @Autowired
    CreateBinary bin;

    ToolKit toolkit = new ToolKit();

    @PostMapping(value = "/upload")
    public String uploadFiles(@RequestParam(value = "files") MultipartFile[] files,
                                                       @RequestParam(value = "properties", required = false) String uploads) throws URISyntaxException {

        String message = "";
        ArrayList<Path> fileNames = new ArrayList<>();
        String submitId = "";
        try {

            String images = bin.toByteString(files);
            ArrayList<Object> atts = toolkit.getAttributes(uploads);
            byte[] product = processor.makeNewPicture(images, atts);
            return bin.byteToString(product);
        } catch (Exception e) {
            message = "Failed to upload files!";
            return message;
        }
    }
}