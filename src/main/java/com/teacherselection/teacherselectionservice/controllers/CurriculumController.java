package com.teacherselection.teacherselectionservice.controllers;

import com.teacherselection.teacherselectionservice.services.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CurriculumController {

    CurriculumService curriculumService;

    @Autowired
    public void setCurriculumService(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("email") String email){
        try {
            curriculumService.saveCurriculum(file, email);
            return  "success";
        } catch ( Exception e){
            e.printStackTrace();
            return  "error " + e.getMessage();
        }
    }

    @RequestMapping(value = "/retrieve" , method = RequestMethod.POST)
    public String retrieveFile(@RequestParam("email") String email) {
        if (curriculumService.findCurriculum(email)){
            return "success";
        } else {
            return "error";
        }
    }

}

