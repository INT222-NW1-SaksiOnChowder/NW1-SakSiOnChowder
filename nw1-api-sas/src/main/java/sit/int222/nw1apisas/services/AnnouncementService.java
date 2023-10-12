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
import sit.int222.nw1apisas.exceptions.AnnouncementNotFoundException;
import sit.int222.nw1apisas.exceptions.BadRequestException;
import sit.int222.nw1apisas.exceptions.UnAuthorizationException;
import sit.int222.nw1apisas.repositories.AnnouncementRepository;

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
        announcement.setPublishDate(announcementItemDto.getPublishDate());
        announcement.setCloseDate(announcementItemDto.getCloseDate());
        announcement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
        announcement.setViewCount(0);
        announcement.setAnnouncementOwner(userService.getUserById(announcementItemDto.getAnnouncementOwner()));
        return announcementRepository.saveAndFlush(announcement);

    }

    public String deleteAnnouncement(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))) {
                announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("The announcement is not found."));
                announcementRepository.deleteById(id);
                return "Delete Announcement id " + id + " Successfully";
            } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_announcer"))) {
                Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("The announcement is not found."));
                if (announcement != null && announcement.getAnnouncementOwner().getUsername().equals(currentPrincipalName)) {
                    announcementRepository.deleteById(id);
                    return "Delete Announcement id " + id + " Successfully";
                }

            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No authorization");
        }
        throw new UnAuthorizationException("Please login first.");

    }

    public Announcement updateAnnouncement(AnnouncementItemDto announcementItemDto, Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))) {
                Announcement existingAnnouncement = announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("The announcement is not found."));
                existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
                existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
                existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
                existingAnnouncement.setPublishDate(announcementItemDto.getPublishDate());
                existingAnnouncement.setCloseDate(announcementItemDto.getCloseDate());
                existingAnnouncement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
                return announcementRepository.saveAndFlush(existingAnnouncement);
            } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_announcer"))) {
                Announcement existingAnnouncement = announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("The announcement is not found."));
                if (existingAnnouncement != null && existingAnnouncement.getAnnouncementOwner().getUsername().equals(currentPrincipalName)) {
                    existingAnnouncement.setAnnouncementTitle(announcementItemDto.getAnnouncementTitle());
                    existingAnnouncement.setAnnouncementDescription(announcementItemDto.getAnnouncementDescription());
                    existingAnnouncement.setCategoryId(categoryService.getCategoryById(announcementItemDto.getCategoryId()));
                    existingAnnouncement.setPublishDate(announcementItemDto.getPublishDate());
                    existingAnnouncement.setCloseDate(announcementItemDto.getCloseDate());
                    existingAnnouncement.setAnnouncementDisplay(announcementItemDto.getAnnouncementDisplay());
                    return announcementRepository.saveAndFlush(existingAnnouncement);
                }
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No authorization");
        }
        throw new UnAuthorizationException("Please login first.");
    }

    public List<Announcement> getAllAnnouncements() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        เอาไว้หาว่าใครล็อกอินอยู่
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))) {
                List<Announcement> announcements = announcementRepository.findAllByOrderByIdDesc();
                if (announcements != null) {
                    return announcements;
                }
            } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_announcer"))) {
                List<Announcement> announcements = announcementRepository.findAnnouncementsByAnnouncementOwner_UsernameOrderByIdDesc(currentPrincipalName);
                if (announcements != null) {
                    return announcements;
                }
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        }
        throw new UnAuthorizationException("Please login first.");

    }

    public Announcement getDetailsById(Integer id, Boolean count) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))) {
                if (id != null && !(id instanceof Integer)) {
                    throw new BadRequestException("id must be integer");
                }
                Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("Announcement id: " + id + " not found"));
                if (count) {
                    announcement.setViewCount(announcement.getViewCount() + 1);
                    announcementRepository.saveAndFlush(announcement);
                }
                return announcement;
            } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_announcer"))) {
                if (id != null && !(id instanceof Integer)) {
                    throw new BadRequestException("id must be integer");
                }
                Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException("Announcement id: " + id + " not found"));
                if (announcement != null && announcement.getAnnouncementOwner().getUsername().equals(currentPrincipalName)) {
                    if (count) {
                        announcement.setViewCount(announcement.getViewCount() + 1);
                        announcementRepository.saveAndFlush(announcement);
                    }
                    return announcement;
                }
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        throw new UnAuthorizationException("Please login first.");
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


