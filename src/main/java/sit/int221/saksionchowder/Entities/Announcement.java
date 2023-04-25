package sit.int221.saksionchowder.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.saksionchowder.Enum.AnnouncementDisplay;
import java.time.ZonedDateTime;

@Entity
@Table(name = "announcements")
@Setter
@Getter

public class Announcement {
    @Id
    private Integer id;

    @Column(name = "announcementTitle", nullable = false, length = 200)
    private String announcementTitle;
    @Column(name = "announcementDescription", nullable = false, length = 10000)
    private String announcementDescription;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category announcementCategory;
    @Column(name = "publishDate", nullable = true)
    private ZonedDateTime publishDate;
    @Column(name = "closeDate", nullable = true)
    private ZonedDateTime closeDate;

    @Enumerated(EnumType.STRING)
    public AnnouncementDisplay announcementDisplay;




}
