package sit.int221.nw1apisas.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Enums.AnnouncementDisplay;
import java.time.ZonedDateTime;
import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT a FROM Announcement a ORDER BY a.id DESC")
    List<Announcement> findAllAnnouncementsByIdDesc();

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND (a.publishDate IS null OR :currentDate>=a.publishDate) AND (a.closeDate IS NULL OR :currentDate < a.closeDate) ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncement(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate);

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND a.closeDate IS NOT NULL AND :currentDate >= a.closeDate ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncement(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate);

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND (a.publishDate IS null OR :currentDate>=a.publishDate) AND (a.closeDate IS NULL OR :currentDate < a.closeDate) ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementWithPagination(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND a.closeDate IS NOT NULL AND :currentDate >= a.closeDate ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementWithPagination(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate, Pageable pageable);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE (a.announcementDisplay = :announcementDisplay) AND (a.publishDate IS NULL OR :currentDate >= a.publishDate) AND (a.closeDate IS NULL OR :currentDate < a.closeDate) AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementByCategoryWithPagination(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate, Pageable pageable, Integer categoryId);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE (a.announcementDisplay = :announcementDisplay) AND a.closeDate IS NOT NULL AND :currentDate >= a.closeDate AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementByCategoryWithPagination(AnnouncementDisplay announcementDisplay, ZonedDateTime currentDate, Pageable pageable, Integer categoryId);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findAllByCategoryWithPagination(Pageable pageable, Integer categoryId);

}
