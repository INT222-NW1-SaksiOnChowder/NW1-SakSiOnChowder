package sit.int221.nw1apisas.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int221.nw1apisas.Dtos.*;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Entities.Category;
import sit.int221.nw1apisas.Services.AnnouncementService;
import sit.int221.nw1apisas.Utils.ListMapper;

import java.time.ZonedDateTime;
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AddAnnouncementItemDto createAnnouncement(@RequestBody AnnouncementItemDto announcementItemDto){
        Announcement createAnnouncement = announcementService.createAnnouncement(announcementItemDto);
        return modelMapper.map(createAnnouncement, AddAnnouncementItemDto.class);

    }


    @DeleteMapping("/{id}")
    public String deleteAnnouncement(@PathVariable Integer id){
            announcementService.deleteAnnouncement(id);
            return "Delete id "+ id + " successfully.";
    }

    @PutMapping("/{id}")
    public AddAnnouncementItemDto updateAnnouncement(@RequestBody AnnouncementItemDto newAnnouncement, @PathVariable Integer id){
        Announcement updateAnnouncement = announcementService.updateAnnouncement(newAnnouncement, id);
        return modelMapper.map(updateAnnouncement, AddAnnouncementItemDto.class);
    }

    @GetMapping("")
    public List<?> getAllAnnouncements(@RequestParam(defaultValue = "admin") String mode){
        List<Announcement> announcements = announcementService.getAllAnnouncements(mode);
        if(mode.equals("active")){
            return listMapper.mapList(announcements, ActiveAnnouncementDto.class, modelMapper);
        }else if(mode.equals("close")){
            return listMapper.mapList(announcements, CloseAnnouncementDto.class, modelMapper);
        }else{
            return listMapper.mapList(announcements, AnnouncementDto.class, modelMapper);
        }
    }


    @GetMapping("/{id}")
    public Object getDetailsById(@PathVariable Integer id, @RequestParam(defaultValue = "admin") String mode){
        Announcement announcements = announcementService.getDetailsById(id);
        if(mode.equals("active")){
            return modelMapper.map(announcements, ActiveAnnouncementDetailDto.class);
        }else if(mode.equals("close")){
            return modelMapper.map(announcements, CloseAnnouncementDetailDto.class);
        }else{
            return modelMapper.map(announcements, AnnouncementDetailDto.class);
        }

    }


    @GetMapping("/pages")
    public PageDto<?> getAnnouncementWithPagination(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    @RequestParam(defaultValue = "active") String mode,
                                                    @RequestParam(required = false) Integer category){
        Page<Announcement> announcementList = announcementService.getAnnouncementWithPagination(page, size, mode, category);
//        if(mode.equals("active")){
//            return listMapper.toPageDTO(announcementList, ActiveAnnouncementDto.class, modelMapper);
//        }else if(mode.equals("close")){
//            return listMapper.toPageDTO(announcementList, CloseAnnouncementDto.class, modelMapper);
//        }else {
            return listMapper.toPageDTO(announcementList, AnnouncementDto.class, modelMapper);
//        }


    }

}
