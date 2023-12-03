package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int222.nw1apisas.properties.FileStorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
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


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
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

    public void deleteFile(String fileName) {
        try {
            Path file = this.fileStorageLocation.resolve(fileName);
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAllFiles() {
        try {
            Files.walk(this.fileStorageLocation)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (file.isFile()) {
                            file.delete();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Error deleting files: " + e.getMessage());
        }
    }


//    public String updateFile(String oldFileName, MultipartFile newFile) {
//        // Normalize file name
//        String fileName = StringUtils.cleanPath(newFile.getOriginalFilename());
//        try {
//            // Check if the file's name contains invalid characters
//            if (fileName.contains("..")) {
//                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            // Get the path of the old file
//            Path oldFilePath = this.fileStorageLocation.resolve(oldFileName).normalize();
//
//            // Delete the old file
//            Files.delete(oldFilePath);
//
//            // Store the new file
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(newFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            return fileName;
//        } catch (IOException ex) {
//            throw new RuntimeException("Could not update file " + fileName + ". Please try again!", ex);
//        }
//    }

//    public String updateFile(String filename, MultipartFile newFile) {
//        // Normalize file name for the new file
//        String newFileName = StringUtils.cleanPath(newFile.getOriginalFilename());
//        try {
//            // Check if the file's name contains invalid characters
//            if (newFileName.contains("..")) {
//                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + newFileName);
//            }
//
//            // Store the new file (using the provided filename as it's meant to be an update)
//            Path targetLocation = this.fileStorageLocation.resolve(filename);
//            Files.copy(newFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            return filename;
//        } catch (IOException ex) {
//            throw new RuntimeException("Could not update file " + filename + ". Please try again!", ex);
//        }
//    }

    public List<String> updateFile(Integer id, String filename) {
        Path folderAnn = this.fileStorageLocation.resolve(id.toString());
        List<String> filenames = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderAnn)) {
            for (Path filePath : directoryStream) {
                String currentFileName = filePath.getFileName().toString();
                System.out.println("Processing file: " + currentFileName);

                // Add the file name to the list
                filenames.add(currentFileName);

                System.out.println("File added: " + currentFileName);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return filenames;
    }


}