package sit.int221.nw1apisas.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public AnnouncementDto getDetailsById(@PathVariable Integer id){
        return modelMapper.map(announcementService.getDetailsById(id), AnnouncementDto.class);
    }

}
