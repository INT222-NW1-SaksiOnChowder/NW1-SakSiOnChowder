package sit.int222.nw1apisas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.nw1apisas.exceptions.BadRequestException;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.services.FileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th", "http://intproj22.sit.kmutt.ac.th", "https://intproj22.sit.kmutt.ac.th", "http://localhost:5173"})
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Integer id,@PathVariable String filename) {
        Resource file = fileService.loadFile(id,filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<List<String>> sendAllFileNameForViewAnnouncement(@PathVariable Integer id){
        return fileService.sendAllFileNameForViewAnnouncement(id);
    }

    @PostMapping("")
    public String fileUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("announcementId") Integer announcementId) {
        if (files.length > 5) {
            throw new BadRequestException("Each upload is limited to a maximum of 5 files.", "file");
        }
        Set<String> fileNames = new HashSet<>();
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();

            // Check if the file already exists in the set
            if (!fileNames.add(originalFileName)) {
                throw new BadRequestException("You have already selected the file name: " + originalFileName, "file");
            }
        }
//        store file
        for (MultipartFile file : files) {
            fileService.store(file,announcementId);
            System.out.println(file);
        }
        return "All files uploaded successfully!";
    }


    @DeleteMapping("/{filename:.+}")
    public void deleteFile(@PathVariable("filename") String fileName) {
        try {
            fileService.deleteFile(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting the file: " + e.getMessage());
        }
    }

    @DeleteMapping("")
    public void deleteAllFiles() {
        fileService.deleteAllFiles();
    }


    @PutMapping("/{id}/{filename:.+}")
    public List<String> updateFile(@PathVariable(required = false) Integer id, @RequestParam("file") String filename){
        return fileService.updateFile(id , filename);
    }



}