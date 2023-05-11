package sit.int221.nw1apisas.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.nw1apisas.Dtos.AnnouncementItemDto;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Enums.AnnouncementDisplay;
import sit.int221.nw1apisas.Exceptions.BadRequestException;
import sit.int221.nw1apisas.Exceptions.ItemNotFoundException;
import sit.int221.nw1apisas.Repositories.AnnouncementRepository;


import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CategoryService categoryService;

    public Announcement createAnnouncement(AnnouncementItemDto announcementItemDto) {
        Announcement announcement = new Announcement();
        if (announcementItemDto.getAnnouncementTitle() != null && !(announcementItemDto.getAnnouncementTitle().isEmpty())
                && announcementItemDto.getAnnouncementTitle().trim() != ""
                && announcementItemDto.getAnnouncementTitle().length() > 0 && announcementItemDto.getAnnouncementTitle().length() <= 200) {
            announcement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        } else {
            throw new BadRequestException("Please fill a announcement title and must be less than or equal 200 characters");
        }
        if (announcementItemDto.getAnnouncementDescription() != null && !(announcementItemDto.getAnnouncementDescription().isEmpty())
                && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() > 0 && announcementItemDto.getAnnouncementDescription().length() <= 10000) {
            announcement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        } else {
            throw new BadRequestException("Please fill a announcement description and must be less than or equal 10000 characters");
        }
        if (announcementItemDto.getCategoryId() != null) {
            announcement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        } else {
            throw new BadRequestException("Please choose a category");
        }
        announcement.setPublishDate(announcementItemDto.getPublishDate());
        announcement.setCloseDate(announcementItemDto.getCloseDate());
        announcement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        return announcementRepository.saveAndFlush(announcement);

    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The announcement is not found"));
        announcementRepository.deleteById(id);
    }

    public Announcement updateAnnouncement(AnnouncementItemDto announcementItemDto, Integer id) {
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The announcement is not found"));
        if (announcementItemDto.getAnnouncementTitle() != null && !(announcementItemDto.getAnnouncementTitle().isEmpty())
                && announcementItemDto.getAnnouncementTitle().trim() != ""
                && announcementItemDto.getAnnouncementTitle().length() > 0 && announcementItemDto.getAnnouncementTitle().length() <= 200) {
            existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        } else {
            throw new BadRequestException("Please fill a announcement title and must be less than or equal 200 characters");
        }
        if (announcementItemDto.getAnnouncementDescription() != null && !(announcementItemDto.getAnnouncementDescription().isEmpty())
                && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() > 0 && announcementItemDto.getAnnouncementDescription().length() <= 10000) {
            existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        } else {
            throw new BadRequestException("Please fill a announcement description and must be less than or equal 10000 characters");
        }
        if (announcementItemDto.getCategoryId() != null) {
            existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        } else {
            throw new BadRequestException("Please fill a category");
        }
        existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        existingAnnouncement.setPublishDate(announcementItemDto.getPublishDate());
        existingAnnouncement.setCloseDate(announcementItemDto.getCloseDate());
        existingAnnouncement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        return announcementRepository.saveAndFlush(existingAnnouncement);
    }

    public List<Announcement> getAllAnnouncements(String mode) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        AnnouncementDisplay announcementDisplay = AnnouncementDisplay.Y;
        if (mode.equals("active")) {
            List<Announcement> announcements = announcementRepository.findActiveAnnouncement(announcementDisplay, currentTime);
            if (announcements == null || announcements.isEmpty()) {
                throw new ItemNotFoundException("No announcement");
            }
            return announcements;
        } else if (mode.equals("close")) {
            List<Announcement> announcements = announcementRepository.findCloseAnnouncement(announcementDisplay, currentTime);
            if (announcements == null || announcements.isEmpty()) {
                throw new ItemNotFoundException("No announcement");
            }
            return announcements;
        } else {
            return announcementRepository.findAllAnnouncementsByIdDesc();
        }

    }

    public boolean isActive(Announcement announcement){
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (announcement.getAnnouncementDisplay() == AnnouncementDisplay.Y && (announcement.getPublishDate() == null
                ||  currentTime.isAfter(announcement.getPublishDate()) || currentTime.isEqual(announcement.getPublishDate()))
                && (announcement.getCloseDate() == null || currentTime.isBefore(announcement.getCloseDate()))){
            return true;
        }
        return false;
    }
    public boolean isClose(Announcement announcement) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (announcement.getAnnouncementDisplay() == AnnouncementDisplay.Y &&
                (announcement.getCloseDate() != null && (currentTime.isAfter(announcement.getCloseDate()) || currentTime.isEqual(announcement.getCloseDate())))) {
            return true;
        }
        return false;
    }

    public Announcement getDetailsById(Integer id) {
        if(id == null){
            throw new BadRequestException("id is need to be an integer");
        }
        Announcement announcement = announcementRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Announcement id "+ id +" does not exist"));
        return announcement;
    }

    public Page<Announcement> getAnnouncementWithPagination(int page, int size, String sortBy, String mode, Integer categoryId) {
        Sort sort = Sort.by(sortBy).descending();
        AnnouncementDisplay announcementDisplayShow = AnnouncementDisplay.Y;
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (mode.equals("active")) {
            if (categoryId != null){
                return announcementRepository.findActiveAnnouncementByCategoryWithPagination(announcementDisplayShow, currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findActiveAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
        } else if (mode.equals("close")) {
            if (categoryId != null){
                return announcementRepository.findCloseAnnouncementByCategoryWithPagination(announcementDisplayShow, currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findCloseAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
        } else {
            if (categoryId!= null){
                return announcementRepository.findAllByCategoryWithPagination(pageRequest, categoryId);
            }
            return announcementRepository.findAll(pageRequest);
        }
    }

}


