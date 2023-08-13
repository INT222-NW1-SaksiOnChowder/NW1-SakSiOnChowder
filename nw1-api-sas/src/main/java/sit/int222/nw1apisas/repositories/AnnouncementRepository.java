package sit.int222.nw1apisas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.enums.AnnouncementDisplay;

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



//    @Query(value = "SELECT * FROM Announcement ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findAllAnnouncementsByIdDesc();
//
//    @Query(value = "SELECT * FROM Announcement WHERE (announcementDisplay = 'Y') AND (publishDate IS null OR :currentDate >= publishDate) AND (closeDate IS NULL OR :currentDate < closeDate) ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findActiveAnnouncement(@Param("currentDate") ZonedDateTime currentDate);
//
//    @Query(value = "SELECT * FROM Announcement WHERE (announcementDisplay = 'Y') AND closeDate IS NOT NULL AND :currentDate >= closeDate ORDER BY id DESC", nativeQuery = true)
//    List<Announcement> findCloseAnnouncement(@Param("currentDate") ZonedDateTime currentDate);
//
//    @Query(value = "SELECT * FROM Announcement WHERE (announcementDisplay = 'Y') AND (publishDate IS NULL OR :currentDate >= publishDate) AND (closeDate IS NULL OR :currentDate < closeDate) ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findActiveAnnouncementWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable);
//
//    @Query(value = "SELECT * FROM Announcement WHERE (announcementDisplay = 'Y') AND closeDate IS NOT NULL AND :currentDate >= closeDate ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findCloseAnnouncementWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable);
//
//    @Query(value = "SELECT * FROM Announcement a JOIN Category c ON a.categoryId = c.categoryId WHERE (announcementDisplay = 'Y') AND (publishDate IS NULL OR :currentDate >= publishDate) AND (closeDate IS NULL OR :currentDate < closeDate) AND c.categoryId = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findActiveAnnouncementByCategoryWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable, @Param("categoryId") Integer categoryId);
//
//    @Query(value = "SELECT * FROM Announcement a JOIN Category c ON a.categoryId = c.categoryId WHERE (announcementDisplay = 'Y') AND closeDate IS NOT NULL AND :currentDate >= closeDate AND c.categoryId = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findCloseAnnouncementByCategoryWithPagination(@Param("currentDate") ZonedDateTime currentDate, Pageable pageable, @Param("categoryId") Integer categoryId);
//
//    @Query(value = "SELECT * FROM Announcement a JOIN Category c ON a.categoryId = c.categoryId WHERE c.categoryId = :categoryId ORDER BY id DESC", nativeQuery = true)
//    Page<Announcement> findAllByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);



}
