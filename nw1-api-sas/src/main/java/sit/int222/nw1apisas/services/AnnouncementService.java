package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.dtos.AnnouncementItemDto;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.enums.AnnouncementDisplay;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.AnnouncementRepository;

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
        announcement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        announcement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
        announcement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        ZonedDateTime publishDate = announcementItemDto.getPublishDate();
        ZonedDateTime closeDate = announcementItemDto.getCloseDate();
        announcement.setPublishDate(publishDate);
        announcement.setCloseDate(closeDate);
        announcement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        announcement.setViewCount(0);
        return announcementRepository.saveAndFlush(announcement);

    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));
        announcementRepository.deleteById(id);
    }

    public Announcement updateAnnouncement(AnnouncementItemDto announcementItemDto, Integer id) {
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));

        existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
        existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());

        existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
        ZonedDateTime publishDate = announcementItemDto.getPublishDate();
        ZonedDateTime closeDate = announcementItemDto.getCloseDate();
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
//            List<Announcement> announcements = announcementRepository.findActiveAnnouncement(currentTime);
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if (mode.equals("close")) {
            List<Announcement> announcements = announcementRepository.findCloseAnnouncement(announcementDisplay, currentTime);
//            List<Announcement> announcements = announcementRepository.findCloseAnnouncement(currentTime);
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if (mode.equals("admin")) {
            return announcementRepository.findAllAnnouncementsByIdDesc();
        } else {
            throw new ItemNotFoundException("Can't find a mode");
        }

    }

    public Announcement getDetailsById(Integer id, Boolean count) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Announcement id " + id + " does not exist."));
        if (count) {
            announcement.setViewCount(announcement.getViewCount() + 1);
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
//                return announcementRepository.findActiveAnnouncementByCategoryWithPagination(currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findActiveAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
//            return announcementRepository.findActiveAnnouncementWithPagination(currentTime, pageRequest);
        } else if (mode.equals("close")) {
            if (categoryId != null) {
                return announcementRepository.findCloseAnnouncementByCategoryWithPagination(announcementDisplayShow, currentTime, pageRequest, categoryId);
//                return announcementRepository.findCloseAnnouncementByCategoryWithPagination(currentTime, pageRequest, categoryId);
            }
            return announcementRepository.findCloseAnnouncementWithPagination(announcementDisplayShow, currentTime, pageRequest);
//            return announcementRepository.findCloseAnnouncementWithPagination(currentTime, pageRequest);
        }
        return Page.empty(pageRequest);
    }

}


