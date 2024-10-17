package com.canvas.persistence.jpa.diary.entity;

import com.canvas.persistence.jpa.common.BaseEntity;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String emotion;
    private Boolean isPublic;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", insertable = false, updatable = false)
    private UserEntity writer;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> imageEntities = new ArrayList<>();
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likeEntities = new ArrayList<>();

    public DiaryEntity(Long id, String content, String emotion, Boolean isPublic, Long writerId) {
        this.id = id;
        this.content = content;
        this.emotion = emotion;
        this.isPublic = isPublic;
        this.writerId = writerId;
    }

}
