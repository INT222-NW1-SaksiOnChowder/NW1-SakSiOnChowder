package sit.int222.nw1apisas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.User;

import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findAllByOrderByIdDesc();

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP >=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncement();

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncement();

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP>=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementWithPagination(Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementWithPagination(Pageable pageable);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS NULL OR CURRENT_TIMESTAMP >= a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);





    @Query("SELECT a FROM Announcement a JOIN a.announcementOwner u  WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP >=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) AND u.username = :username ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncementWithRoleAnnouncer(@Param("username") String username);

    @Query("SELECT a FROM Announcement a JOIN a.announcementOwner u WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate AND u.username = :username ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncementWithRoleAnnouncer(@Param("username") String username);

    List<Announcement> findAnnouncementsByAnnouncementOwner_UsernameOrderByIdDesc(String username);
}
