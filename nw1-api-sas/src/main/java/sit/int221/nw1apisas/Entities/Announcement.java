package sit.int221.nw1apisas.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.nw1apisas.Enums.AnnouncementDisplay;

import java.time.ZonedDateTime;

@Entity
@Table(name = "Announcements")
@Setter
@Getter

public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "announcementTitle", nullable = false, length = 200)
    private String announcementTitle;
    @Column(name = "announcementDescription", nullable = false, length = 10000)
    private String announcementDescription;

    @Column(name = "publishDate", nullable = true)
    private ZonedDateTime publishDate;
    @Column(name = "closeDate", nullable = true)
    private ZonedDateTime closeDate;

    @Enumerated(EnumType.STRING)
    private AnnouncementDisplay announcementDisplay;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category categoryId;

}
