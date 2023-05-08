package sit.int221.nw1apisas.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.nw1apisas.Dtos.AnnouncementItemDto;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Repositories.AnnouncementRepository;
import sit.int221.nw1apisas.Repositories.CategoryRepository;

import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllAnnouncementsByIdDesc();
        return announcements;
    }

    public Announcement getDetailsById(Integer id) {
        if (!(id instanceof Integer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return announcementRepository.findById(id).orElseThrow(() -> new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Announcement id" + id + "does not exist"));
        }
    }

    public Announcement createAnnouncement(AnnouncementItemDto announcementItemDto) {
        Announcement announcement = new Announcement();
        if(announcementItemDto.getAnnouncementTitle()!=null && announcementItemDto.getAnnouncementTitle().trim() != ""
                && announcementItemDto.getAnnouncementTitle().length() > 0 && announcementItemDto.getAnnouncementTitle().length() <= 200){
            announcement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill the announcement title and must be less than or equal 200 characters");
        }
        if(announcementItemDto.getAnnouncementDescription()!=null && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() > 0 && announcementItemDto.getAnnouncementDescription().length() <= 10000){
            announcement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill the announcement description and must be less than or equal 10000 characters");
        }
        announcement.setPublishDate(announcementItemDto.getPublishDate());
        announcement.setCloseDate(announcementItemDto.getCloseDate());
        announcement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        announcement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        return announcementRepository.saveAndFlush(announcement);

    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The announcement is not found"));
        announcementRepository.deleteById(id);
    }

    public Announcement updateAnnouncement(AnnouncementItemDto announcementItemDto, Integer id) {
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The announcement is not found"));
        if(announcementItemDto.getAnnouncementTitle()!=null && announcementItemDto.getAnnouncementTitle().trim() != ""
                && announcementItemDto.getAnnouncementTitle().length() > 0 && announcementItemDto.getAnnouncementTitle().length() <= 200){
            existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill the announcement title and must be less than or equal 200 characters");
        }
        if(announcementItemDto.getAnnouncementDescription()!=null && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() > 0 && announcementItemDto.getAnnouncementDescription().length() <= 10000){
            existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please fill the announcement description and must be less than or equal 10000 characters");
        }
        existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        existingAnnouncement.setPublishDate(announcementItemDto.getPublishDate());
        existingAnnouncement.setCloseDate(announcementItemDto.getCloseDate());
        existingAnnouncement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        return announcementRepository.saveAndFlush(existingAnnouncement);
    }


}


