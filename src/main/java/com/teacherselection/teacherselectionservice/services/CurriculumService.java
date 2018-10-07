package com.teacherselection.teacherselectionservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface CurriculumService {
    void saveCurriculum(MultipartFile file, String email);
    Boolean findCurriculum(String email);
}
