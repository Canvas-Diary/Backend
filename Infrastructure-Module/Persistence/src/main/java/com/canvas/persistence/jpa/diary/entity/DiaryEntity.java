package com.canvas.persistence.jpa.diary.entity;

import com.canvas.persistence.jpa.common.BaseEntity;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class DiaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String emotion;
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity writer;

}
