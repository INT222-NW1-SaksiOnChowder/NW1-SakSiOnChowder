package sit.int221.nw1apisas.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.nw1apisas.Dtos.AnnouncementDto;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Entities.Category;
import sit.int221.nw1apisas.Repositories.AnnouncementRepository;
import sit.int221.nw1apisas.Repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllAnnouncementsByIdDesc();
        return announcements;
    }

    public Announcement getDetailsById(Integer id) {
        if(!(id instanceof Integer)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else{
            return announcementRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Announcement id" + id + "does not exist"));
        }
    }

    public Announcement createAnnouncement(Announcement announcement) {
        Category category = categoryRepository.findById(announcement.getCategoryId().getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found"));
        announcement.setCategoryId(category);

        return announcementRepository.saveAndFlush(announcement);
    }
}

