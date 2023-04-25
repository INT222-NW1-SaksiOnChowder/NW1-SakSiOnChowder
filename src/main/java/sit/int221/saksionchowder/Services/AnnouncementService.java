package sit.int221.saksionchowder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.saksionchowder.Entities.Announcement;
import sit.int221.saksionchowder.Repositories.AnnouncementRepository;


import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllAnnouncementsByIdDesc();
        return announcements;
    }

    public Announcement getDetailsById(Integer id){
        Announcement announcement = announcementRepository.findById(id).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return announcement;
    }





}
