package sit.int221.nw1apisas.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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



//    @Query(value = "SELECT * FROM announcement ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findAllAnnouncementsByIdDesc();
//
//    @Query(value = "SELECT * FROM announcement WHERE (announcement_display = 'Y') AND (publish_date IS null OR :currentDate >= publish_date) AND (close_date IS NULL OR :currentDate < close_date) ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findActiveAnnouncement(@Param("currentDate") ZonedDateTime currentDate);
//
//    @Query(value = "SELECT * FROM announcement WHERE (announcement_display = 'Y') AND close_date IS NOT NULL AND :currentDate >= close_date ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findCloseAnnouncement(@Param("currentDate") ZonedDateTime currentDate);
//
//    @Query(value = "SELECT * FROM announcement WHERE (announcement_display = 'Y') AND (publish_date IS NULL OR :currentDate >= publish_date) AND (close_date IS NULL OR :currentDate < close_date) ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findActiveAnnouncementWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable);
//
//    @Query(value = "SELECT * FROM announcement WHERE (announcement_display = 'Y') AND close_date IS NOT NULL AND :currentDate >= close_date ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findCloseAnnouncementWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable);
//
//    @Query(value = "SELECT * FROM announcement JOIN category c ON category_id = c.category_id WHERE (announcement_display = 'Y') AND (publish_date IS NULL OR :currentDate >= publish_date) AND (close_date IS NULL OR :currentDate < close_date) AND c.category_id = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findActiveAnnouncementByCategoryWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable, @Param("categoryId") Integer categoryId);
//
//    @Query(value = "SELECT * FROM announcement JOIN category c ON category_id = c.category_id WHERE (announcement_display = 'Y') AND close_date IS NOT NULL AND :currentDate >= close_date AND c.category_id = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findCloseAnnouncementByCategoryWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable, @Param("categoryId") Integer categoryId);
//
//    @Query(value = "SELECT * FROM announcement JOIN category c ON category_id = c.category_id WHERE c.category_id = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findAllByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);



}
