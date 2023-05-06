package sit.int221.nw1apisas.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Repositories.AnnouncementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllAnnouncementsByIdDesc();
        return announcements;
    }

    public Announcement getDetailsById(Integer id) {
        if(!(id instanceof Integer)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }else{
            return announcementRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Announcement id" + id + "does not exist"));
        }
    }
}

