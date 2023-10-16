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

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP>=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementWithPagination(Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementWithPagination(Pageable pageable);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS NULL OR CURRENT_TIMESTAMP >= a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findActiveAnnouncementByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);

    @Query("SELECT a FROM Announcement a JOIN a.categoryId c WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate AND c.categoryId = :categoryId ORDER BY a.id DESC")
    Page<Announcement> findCloseAnnouncementByCategoryWithPagination(Pageable pageable, @Param("categoryId") Integer categoryId);


    List<Announcement> findAnnouncementsByAnnouncementOwner_UsernameOrderByIdDesc(String username);

    List<Announcement> findAllByAnnouncementOwner(User username);

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP >=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncementWithRoleAdminAndViewer();

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncementWithRoleAdminAndViewer();
    @Query("SELECT a FROM Announcement a JOIN User u WHERE a.announcementDisplay = 'Y' AND (a.publishDate IS null OR CURRENT_TIMESTAMP >=a.publishDate) AND (a.closeDate IS NULL OR CURRENT_TIMESTAMP < a.closeDate) AND a.announcementOwner.username = :currentPrincipleName ORDER BY a.id DESC")
    List<Announcement> findActiveAnnouncementWithRoleAnnouncer(@Param("currentPrincipleName") String currentPrincipleName);

    @Query("SELECT a FROM Announcement a JOIN User u WHERE a.announcementDisplay = 'Y' AND a.closeDate IS NOT NULL AND CURRENT_TIMESTAMP >= a.closeDate AND a.announcementOwner.username = :currentPrincipleName ORDER BY a.id DESC")
    List<Announcement> findCloseAnnouncementWithRoleAnnouncer(@Param("currentPrincipleName") String currentPrincipleName);
}
