package sit.int221.nw1apisas.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int221.nw1apisas.Dtos.AnnouncementItemDto;
import sit.int221.nw1apisas.Dtos.AnnouncementIdDto;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Dtos.AnnouncementDto;
import sit.int221.nw1apisas.Services.AnnouncementService;
import sit.int221.nw1apisas.Utils.ListMapper;

import java.util.List;


@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    @GetMapping("")
    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return listMapper.mapList(announcements, AnnouncementDto.class, modelMapper);
    }

    @GetMapping("/{id}")
    public AnnouncementIdDto getDetailsById(@PathVariable Integer id){
        return modelMapper.map(announcementService.getDetailsById(id), AnnouncementIdDto.class);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AnnouncementItemDto createAnnouncement(@RequestBody Announcement announcement){
        return modelMapper.map(announcementService.createAnnouncement(announcement), AnnouncementItemDto.class);
    }

    @DeleteMapping("/{id}")
    public String deleteAnnouncement(@PathVariable Integer id){
         announcementService.deleteAnnouncement(id);
         return "Delete the announcement successfully";
    }

    @PutMapping("/{id}")
    public AnnouncementItemDto updateAnnouncement(@RequestBody Announcement announcement, @PathVariable Integer id){
        return modelMapper.map(announcementService.updateAnnouncement(announcement, id), AnnouncementItemDto.class);
    }
}
