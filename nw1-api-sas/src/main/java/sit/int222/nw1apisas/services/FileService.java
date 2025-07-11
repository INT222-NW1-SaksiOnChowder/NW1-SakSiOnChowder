package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.nw1apisas.exceptions.BadRequestException;
import sit.int222.nw1apisas.properties.FileStorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String store(MultipartFile file, Integer announcementId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Create a directory for the announcement if it doesn't exist
            Path announcementDirectory = this.fileStorageLocation.resolve(announcementId.toString());
            Files.createDirectories(announcementDirectory);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = announcementDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public ResponseEntity<List<String>> sendAllFileNameForViewAnnouncement(Integer id) {
        Path existsPath = this.fileStorageLocation.resolve(id.toString());

        if (Files.exists(existsPath) && Files.isDirectory(existsPath)) {
            try {
                List<String> fileNames = Files.list(existsPath)
                        .map(filePath -> filePath.getFileName().toString())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(fileNames);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(Collections.emptyList());
            }
        } else {
            System.out.println("Directory does not exist");
            return ResponseEntity.notFound().build();
        }
    }


    public Resource loadFile(Integer id, String fileName) {
        try {
            Path directoryPath = this.fileStorageLocation.resolve(id.toString());
            Path filePath = directoryPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File operation error: " + fileName, ex);
        }
    }

    public void deleteFile(String fileName, Integer id) {
        try {
            // สร้าง Path ที่ชี้ไปที่โฟลเดอร์ด้วย ID
            Path folderPath = this.fileStorageLocation.resolve(id.toString());
            // ตรวจสอบว่าโฟลเดอร์ด้วย ID ที่ระบุมีอยู่หรือไม่
            if (!Files.exists(folderPath) || !Files.isDirectory(folderPath)) {
                throw new RuntimeException("Directory not found for ID: " + id);
            }
            // สร้าง Path ที่ชี้ไปยังไฟล์ที่ต้องการลบในโฟลเดอร์นี้
            Path filePath = folderPath.resolve(fileName);
            // ตรวจสอบว่าไฟล์ที่ต้องการลบอยู่ในโฟลเดอร์หรือไม่ และลบไฟล์
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                Files.delete(filePath);
            } else {
                throw new RuntimeException("File not found in directory for ID: " + id);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage());
        }
    }


    public void deleteAllFiles(Integer id) {
        try {
            Path folderToDelete = Paths.get(this.fileStorageLocation.toString(), id.toString());

            if (Files.exists(folderToDelete) && Files.isDirectory(folderToDelete)) {
                Files.walk(folderToDelete)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(file -> {
                            if (file.isFile()) {
                                file.delete();
                            }
                        });
                // ลบโฟลเดอร์หลังจากลบไฟล์ทั้งหมด
                Files.delete(folderToDelete);
            } else {
                throw new IllegalArgumentException("Folder with ID " + id + " does not exist or is not a directory");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting files: " + e.getMessage());
        }
    }


    public String updateFile(MultipartFile[] files, Integer announcementId) {
        Path annFolder = this.fileStorageLocation.resolve(announcementId.toString());
        try {
            if (!Files.exists(annFolder)) {
                Files.createDirectories(annFolder);
            }

            List<String> fileNames = Files.list(annFolder)
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());

            Set<String> uniqueFileNames = new HashSet<>();
            for (MultipartFile file : files) {
                if (!uniqueFileNames.add(file.getOriginalFilename())) {
                    throw new BadRequestException("You have already selected the file name: " + file.getOriginalFilename(), "file");
                }

                // Check if the file count exceeds the limit
                if (fileNames.size() + uniqueFileNames.size() > 5 && fileNames.stream().noneMatch(existsFile -> existsFile.equals(file.getOriginalFilename()))) {
                    throw new BadRequestException("Each upload is limited to a maximum of 5 files.", "file");
                }

                store(file, announcementId);
            }
            return "Update File Successful";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}