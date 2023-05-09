package sit.int221.nw1apisas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Enums.AnnouncementDisplay;

import java.time.ZonedDateTime;
import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT a FROM Announcement a ORDER BY a.id DESC")
    List<Announcement> findAllAnnouncementsByIdDesc();

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND (a.publishDate IS null OR :currentDate>=a.publishDate) AND (a.closeDate IS NULL OR :currentDate < a.closeDate) ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncement(@Param("announcementDisplay") AnnouncementDisplay announcementDisplay, @Param("currentDate") ZonedDateTime currentDate);

    @Query("SELECT a FROM Announcement a WHERE (a.announcementDisplay = :announcementDisplay) AND :currentDate >= a.closeDate ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncement(@Param("announcementDisplay") AnnouncementDisplay announcementDisplay, @Param("currentDate") ZonedDateTime currentDate);

}
