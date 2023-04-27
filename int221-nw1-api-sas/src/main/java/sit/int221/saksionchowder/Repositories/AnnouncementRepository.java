package sit.int221.saksionchowder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import sit.int221.saksionchowder.Entities.Announcement;

import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query("SELECT a FROM Announcement a ORDER BY a.id DESC")
    List<Announcement> findAllAnnouncementsByIdDesc();
}
