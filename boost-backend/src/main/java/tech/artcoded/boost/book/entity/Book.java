package tech.artcoded.boost.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.artcoded.boost.common.entity.Auditable;
import tech.artcoded.boost.upload.entity.Upload;
import tech.artcoded.boost.user.entity.User;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "book")
public class Book extends Auditable<String> {

    @Version
    private Long version;

    @Id
    @Column(name = "book_id")
    @GeneratedValue
    private Long id;

    @Column(name = "book_title")
    private String title;
    @Column(name = "book_author")
    private String author;

    @Column(name = "book_category")
    private String category;

    @Column(name = "book_description", length = 2048)
    private String description;

    @Column(name = "book_total_duration")
    private long totalDuration; // probably in millis

    @OneToOne
    @JoinColumn(referencedColumnName = "upload_id")
    private Upload cover;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "usr_id",name = "book_user_id")
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER)
    private List<Star> stars;

}
