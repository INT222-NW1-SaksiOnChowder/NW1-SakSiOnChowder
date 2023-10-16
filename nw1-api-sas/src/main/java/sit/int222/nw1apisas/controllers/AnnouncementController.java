package sit.int222.nw1apisas.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.dtos.announcements.*;
import sit.int222.nw1apisas.dtos.pagination.PageDto;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.exceptions.UnAuthorizationException;
import sit.int222.nw1apisas.services.AnnouncementService;
import sit.int222.nw1apisas.utils.ListMapper;

import java.util.List;


@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th","http://intproj22.sit.kmutt.ac.th","https://intproj22.sit.kmutt.ac.th","http://localhost:5173"})
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AddUpdateAnnouncementItemDto createAnnouncement(@RequestBody @Valid AnnouncementItemDto announcementItemDto) {
        Announcement createAnnouncement = announcementService.createAnnouncement(announcementItemDto);
        return modelMapper.map(createAnnouncement, AddUpdateAnnouncementItemDto.class);

    }


    @DeleteMapping("/{id}")
    public String deleteAnnouncement(@PathVariable Integer id) {
        return announcementService.deleteAnnouncement(id);
    }

    @PutMapping("/{id}")
    public AddUpdateAnnouncementItemDto updateAnnouncement(@RequestBody @Valid AnnouncementItemDto newAnnouncement, @PathVariable Integer id) {
        Announcement updateAnnouncement = announcementService.updateAnnouncement(newAnnouncement, id);
        return modelMapper.map(updateAnnouncement, AddUpdateAnnouncementItemDto.class);
    }

    @GetMapping("")
    public List<?> getAllAnnouncements(@RequestParam(defaultValue = "all",required = false) String mode) {
        List<Announcement> announcements = announcementService.getAllAnnouncements(mode);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))){
            return listMapper.mapList(announcements, ResponseAllAnnouncementForAdmin.class, modelMapper);
        }else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_announcer"))){
            return listMapper.mapList(announcements, ResponseAllAnnouncementForAnnouncer.class, modelMapper);
        }
        throw new UnAuthorizationException("Please Login first");

    }


    @GetMapping("/{id}")
    public Object getDetailsById(@PathVariable Integer id,
                                 @RequestParam(defaultValue = "all", required = false) String mode,
                                 @RequestParam(defaultValue = "false", required = false) Boolean count) {
        Announcement announcements = announcementService.getDetailsById(id, count);
        if (mode.equals("active")) {
            return modelMapper.map(announcements, ActiveAnnouncementDetailDto.class);
        } else if (mode.equals("close")) {
            return modelMapper.map(announcements, CloseAnnouncementDetailDto.class);
        } else {
            return modelMapper.map(announcements, AnnouncementDetailDto.class);
        }
    }


    @GetMapping("/pages")
    public PageDto<?> getAnnouncementWithPagination(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    @RequestParam(defaultValue = "active") String mode,
                                                    @RequestParam(required = false) Integer category) {
        Page<Announcement> announcementList = announcementService.getAnnouncementWithPagination(page, size, mode, category);
        return listMapper.toPageDTO(announcementList, AnnouncementDto.class, modelMapper);
    }

}
