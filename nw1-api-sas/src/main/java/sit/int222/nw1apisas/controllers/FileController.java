package sit.int222.nw1apisas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.nw1apisas.services.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadFileAsResource(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
    @PostMapping("")
    public String fileUpload(@RequestParam("file") MultipartFile[] files) {
        if (files.length > 5) {
            return "Each upload is limited to a maximum of 5 files.";
        }

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            // Check if the file already exists
            if (fileService.fileExists(originalFileName)) {
                return "File with name " + originalFileName + " already exists!";
            }
        }

        // If no file with duplicate names found and the number of files is within the limit, proceed to store all files
        for (MultipartFile file : files) {
            fileService.store(file);
        }

        return "All files uploaded successfully!";
    }
}