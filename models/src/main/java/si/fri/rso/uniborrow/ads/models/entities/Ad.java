package si.fri.rso.uniborrow.ads.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "target_audience")
    @Enumerated(EnumType.STRING)
    private TargetAudience targetAudience;

    @Column(name = "url")
    private String url;

    @Column(name = "image_url")
    private String imageUrl;

}

