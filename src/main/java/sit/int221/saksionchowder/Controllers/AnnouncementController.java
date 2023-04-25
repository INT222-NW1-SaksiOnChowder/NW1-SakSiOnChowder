package sit.int221.saksionchowder.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.saksionchowder.Entities.Announcement;
import sit.int221.saksionchowder.Services.AnnouncementService;

import java.util.List;


@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("")
    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        if (announcements.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No any Announcements");
        }
        return announcements;
    }

    @GetMapping("/{id}")
    public Announcement getDetailsById(@PathVariable Integer id){
        return announcementService.getDetailsById(id);
    }

}
