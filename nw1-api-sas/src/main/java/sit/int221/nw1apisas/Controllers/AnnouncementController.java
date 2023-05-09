package sit.int221.nw1apisas.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int221.nw1apisas.Dtos.*;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Services.AnnouncementService;
import sit.int221.nw1apisas.Utils.ListMapper;

import java.util.List;


@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
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
    public AnnouncementDetailDto getDetailsById(@PathVariable Integer id){
        return modelMapper.map(announcementService.getDetailsById(id), AnnouncementDetailDto.class);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AddAnnouncementItemDto createAnnouncement(@RequestBody AnnouncementItemDto announcementItemDto){
        Announcement createAnnouncement = announcementService.createAnnouncement(announcementItemDto);
        return modelMapper.map(createAnnouncement, AddAnnouncementItemDto.class);

    }


    @DeleteMapping("/{id}")
    public String deleteAnnouncement(@PathVariable Integer id){
            announcementService.deleteAnnouncement(id);
            return "Delete id "+ id + " successfully";
    }

    @PutMapping("/{id}")
    public AddAnnouncementItemDto updateAnnouncement(@RequestBody AnnouncementItemDto newAnnouncement, @PathVariable Integer id){
        Announcement updateAnnouncement = announcementService.updateAnnouncement(newAnnouncement, id);
        return modelMapper.map(updateAnnouncement, AddAnnouncementItemDto.class);
    }

    @GetMapping("/user")
    public List<AnnouncementDto> getUserViewAnnouncement(@RequestParam(defaultValue = "admin") String mode){
        List<Announcement> announcements = announcementService.getUserViewAnnouncement(mode);
        return listMapper.mapList(announcements, AnnouncementDto.class, modelMapper);
    }

}
