package sit.int222.nw1apisas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.nw1apisas.exceptions.BadRequestException;
import sit.int222.nw1apisas.services.FileService;

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
    public ResponseEntity<Resource> serveFile(@PathVariable Integer id, @PathVariable String filename) {
        Resource file = fileService.loadFile(id, filename);

        // Determine the MediaType based on the file extension
        MediaType mediaType = determineMediaType(filename);
        if (mediaType.equals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(mediaType)  // Use the determined MediaType directly
                    .body(file);
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    private MediaType determineMediaType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        switch (extension) {
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "docx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            default:
                // Return a default media type for unrecognized types
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<List<String>> sendAllFileNameForViewAnnouncement(@PathVariable Integer id) {
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
            fileService.store(file, announcementId);
            System.out.println(file);
        }
        return "All files uploaded successfully!";
    }


    //    @DeleteMapping("/{id}/{filename:.+}")
//    public void deleteFile(@PathVariable("id") Integer id, @PathVariable("filename") String fileName) {
//        try {
//            fileService.deleteFile(fileName, id);
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting the file: " + e.getMessage());
//        }
//    }
    @DeleteMapping("/delete/{id}")
    public void deleteFiles(@PathVariable("id") Integer id, @RequestParam("files") String[] filenames) {
        for (String fileName : filenames) {
            try {
                fileService.deleteFile(fileName, id);
            } catch (Exception e) {
                throw new RuntimeException("Error deleting the file '" + fileName + "' for ID " + id + ": " + e.getMessage());
            }
        }
    }


    @DeleteMapping("/{id}")
    public void deleteAllFiles(@PathVariable("id") Integer id) {
        fileService.deleteAllFiles(id);
    }


    @PutMapping("/{id}")
    public String updateFile(@RequestParam("file") MultipartFile[] files, @PathVariable Integer id) {
        return fileService.updateFile(files, id);
    }


}