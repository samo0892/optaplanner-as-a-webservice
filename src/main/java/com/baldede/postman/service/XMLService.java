package com.baldede.postman.service;

import com.baldede.postman.domain.Tour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Service
public class XMLService {

    private final Logger logger = LoggerFactory.getLogger(XMLService.class);
    public Tour tour;


    public Tour parseStop(String filePath) {
        try {
            File file = new File (filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(Tour.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            tour = (Tour) jaxbUnmarshaller.unmarshal(file);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return tour;
    }
}
