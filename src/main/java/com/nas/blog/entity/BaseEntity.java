package com.nas.blog.entity;

import com.nas.blog.util.constant.CommonConstant;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 상속 받는 자식 클래스에 공통적인 매핑 정보만 제공 -> 추상클래스
@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column(nullable = false, length = 1)
    private String deleteFlg;

    @PrePersist
    public void prePersist(){
        deleteFlg = CommonConstant.DELETE_FLG_FALSE;
    }
}
