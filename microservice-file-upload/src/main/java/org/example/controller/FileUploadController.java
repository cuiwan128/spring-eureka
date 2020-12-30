package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public @ResponseBody String handFileUpload(@RequestParam(value = "file",required = true) MultipartFile file) throws Exception {
       byte[] bytes = file.getBytes();
        String path="I:/"+new Date().getTime()+file.getOriginalFilename();
        File fileSave = new File(path);
        file.transferTo(fileSave);
        file = null;
        //file = null;
       //FileCopyUtils.copy(bytes,fileSave);
       return  path;
    }

}
