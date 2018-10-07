package com.teacherselection.teacherselectionservice.services;

import com.teacherselection.teacherselectionservice.entities.Curriculum;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;

@Service
public class CurriculumServiceImpl implements  CurriculumService {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void saveCurriculum(MultipartFile file, String email) {
        try {
            Curriculum curriculum = new Curriculum();
            curriculum.setEmail(email);
            curriculum.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            mongoOperations.insert(curriculum);
        } catch (Exception e) {
            System.err.println("Exception: CurriculumServiceImpl:saveCurriculum " +  e.getMessage());
        }
    }

    @Override
    public Boolean findCurriculum(String email) {
        try {
            Curriculum curriculum = mongoOperations.findOne(new BasicQuery("{email: \"" + email + "\"}"), Curriculum.class);
            Binary binary = curriculum.getFile();
            if (binary != null) {
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = new FileOutputStream(curriculum.getId()+ ".pdf");
                    fileOutputStream.write(binary.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                fileOutputStream.close();
            }
        } catch (Exception e) {
            System.err.println("Exception: CurriculumServiceImpl:findCurriculum " +  e.getMessage());
            return false;
        }
        return true;
    }


}
