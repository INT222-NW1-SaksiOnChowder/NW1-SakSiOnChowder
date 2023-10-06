package sit.int222.nw1apisas.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String announcementDisplay;

    @Column(name = "viewCount", nullable = false)
    private Integer viewCount;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "announcementOwner", referencedColumnName = "id", nullable = false)
    private User announcementOwner;

}
