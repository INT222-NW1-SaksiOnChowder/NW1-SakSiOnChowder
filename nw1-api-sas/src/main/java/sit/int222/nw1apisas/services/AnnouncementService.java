package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.announcements.AnnouncementItemDto;
import sit.int222.nw1apisas.entities.Announcement;
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

    @Autowired
    private UserService userService;

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
        announcement.setAnnouncementOwner(userService.getUserById(announcementItemDto.getAnnouncementOwner()));
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
        if (mode.equals("active")) {
            List<Announcement> announcements = announcementRepository.findActiveAnnouncement();
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if (mode.equals("close")) {
            List<Announcement> announcements = announcementRepository.findCloseAnnouncement();
            if (announcements == null || announcements.size() == 0) {
                throw new ItemNotFoundException("No announcement.");
            }
            return announcements;
        } else if (mode.equals("admin")) {
            return announcementRepository.findAllByOrderByIdDesc();
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
        PageRequest pageRequest = PageRequest.of(page, size);
        if (mode.equals("active")) {
            if (categoryId != null) {
                return announcementRepository.findActiveAnnouncementByCategoryWithPagination(pageRequest, categoryId);
            }
            return announcementRepository.findActiveAnnouncementWithPagination(pageRequest);
        } else if (mode.equals("close")) {
            if (categoryId != null) {
                return announcementRepository.findCloseAnnouncementByCategoryWithPagination(pageRequest, categoryId);
            }
            return announcementRepository.findCloseAnnouncementWithPagination(pageRequest);
        }
        return Page.empty(pageRequest);
    }

}


