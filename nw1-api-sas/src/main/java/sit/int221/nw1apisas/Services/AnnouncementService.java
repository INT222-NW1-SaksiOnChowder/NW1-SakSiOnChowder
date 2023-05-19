package sit.int221.nw1apisas.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
                && announcementItemDto.getAnnouncementTitle().length() <= 200) {
            announcement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        } else {
            throw new BadRequestException("Announcement title must between 1 and 200 characters.");
        }
        if (announcementItemDto.getAnnouncementDescription() != null && !(announcementItemDto.getAnnouncementDescription().isEmpty())
                && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() <= 10000) {
            announcement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        } else {
            throw new BadRequestException("Announcement description must between 1 and 10000 characters.");
        }
        if (announcementItemDto.getCategoryId() == null) {
            throw new BadRequestException("Please choose a category.");
        } else {
            announcement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        }
        ZonedDateTime publishDate = announcementItemDto.getPublishDate();
        ZonedDateTime closeDate = announcementItemDto.getCloseDate();
//        ZonedDateTime now = ZonedDateTime.now();
//        if (publishDate != null && closeDate != null && (closeDate.isBefore(publishDate) || closeDate.isEqual(publishDate))) {
//            throw new BadRequestException("The closeDate must be later than publish date.");
//        }
//        if (publishDate != null && publishDate.isBefore(now)) {
//            throw new BadRequestException("The publishDate must be a future date.");
//        }
//        if (closeDate != null && closeDate.isBefore(now)) {
//            throw new BadRequestException("The closeDate must be a future date.");
//        }
        announcement.setPublishDate(publishDate);
        announcement.setCloseDate(closeDate);
        announcement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        return announcementRepository.saveAndFlush(announcement);

    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));
        announcementRepository.deleteById(id);
    }

    public Announcement updateAnnouncement(AnnouncementItemDto announcementItemDto, Integer id) {
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));
        if (announcementItemDto.getAnnouncementTitle() != null && !(announcementItemDto.getAnnouncementTitle().isEmpty())
                && announcementItemDto.getAnnouncementTitle().trim() != ""
                && announcementItemDto.getAnnouncementTitle().length() > 0 && announcementItemDto.getAnnouncementTitle().length() <= 200) {
            existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        } else {
            throw new BadRequestException("Announcement title must between 1 and 200 characters.");
        }
        if (announcementItemDto.getAnnouncementDescription() != null && !(announcementItemDto.getAnnouncementDescription().isEmpty())
                && announcementItemDto.getAnnouncementDescription().trim() != ""
                && announcementItemDto.getAnnouncementDescription().length() > 0 && announcementItemDto.getAnnouncementDescription().length() <= 10000) {
            existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        } else {
            throw new BadRequestException("Announcement description must between 1 and 10000 characters.");
        }
        if (announcementItemDto.getCategoryId() == null) {
            throw new BadRequestException("Please choose a category.");
        } else {
            existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        }
        ZonedDateTime publishDate = announcementItemDto.getPublishDate();
        ZonedDateTime closeDate = announcementItemDto.getCloseDate();
//        ZonedDateTime now = ZonedDateTime.now();
//        if (publishDate != null && closeDate != null && (closeDate.isBefore(publishDate) || closeDate.isEqual(publishDate))) {
//            throw new BadRequestException("The closeDate must be later than publish date.");
//        }
//        if (publishDate != null && publishDate.isBefore(now)) {
//            throw new BadRequestException("The publishDate must be a future date.");
//        }
//        if (closeDate != null && closeDate.isBefore(now)) {
//            throw new BadRequestException("The closeDate must be a future date.");
//        }
        existingAnnouncement.setPublishDate(publishDate);
        existingAnnouncement.setCloseDate(closeDate);
        existingAnnouncement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        return announcementRepository.saveAndFlush(existingAnnouncement);
    }

    public List<Announcement> getAllAnnouncements(String mode) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        AnnouncementDisplay announcementDisplay = AnnouncementDisplay.Y;
        if (mode.equals("active")) {
            List<Announcement> announcements = announcementRepository.findActiveAnnouncement(announcementDisplay, currentTime);
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if (mode.equals("close")) {
            List<Announcement> announcements = announcementRepository.findCloseAnnouncement(announcementDisplay, currentTime);
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if(mode.equals("admin")) {
            return announcementRepository.findAllAnnouncementsByIdDesc();
        }else {
            throw new ItemNotFoundException("Can't find a mode");
        }

    }

    public Announcement getDetailsById(Integer id, Boolean count) {
        if (id == null) {
            throw new BadRequestException("The request page is not available.");
        }
        Announcement announcement = announcementRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Announcement id " + id + " does not exist."));
        if (count){
            announcement.setViewCount(announcement.getViewCount()+1);
        }

        return announcementRepository.saveAndFlush(announcement);

    }

    public Page<Announcement> getAnnouncementWithPagination(int page, int size, String mode, Integer categoryId) {
        AnnouncementDisplay announcementDisplayShow = AnnouncementDisplay.Y;
        PageRequest pageRequest = PageRequest.of(page, size);
        ZonedDateTime currentTime = ZonedDateTime.now();
        if (mode.equals("active")) {
            if (categoryId != null) {
                return announcementRepository.findActiveAnnouncementByCategoryWithPagination(announcementDisplayShow, currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findActiveAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
        } else if(mode.equals("close")){
            if (categoryId != null) {
                return announcementRepository.findCloseAnnouncementByCategoryWithPagination(announcementDisplayShow, currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findCloseAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
        }
        return Page.empty(pageRequest);
//        else {
//            if (categoryId != null) {
//                return announcementRepository.findAllByCategoryWithPagination(pageRequest, categoryId);
//            }
//            return announcementRepository.findAll(pageRequest);
//        }

    }

}


