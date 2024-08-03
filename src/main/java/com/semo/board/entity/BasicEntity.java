package com.semo.board.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
public class BasicEntity implements Serializable {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //생성일
    @Column(name = "create_date")
    private Date createDate;

    //업데이트일
    @Column(name = "update_date")
    private Date updateDate;

    //삭제일
    @Column(name = "delete_date")
    private Date deleteDate;

    //삭제 여부
    @Column(name = "deleted")
    private Boolean deleted = false;
    @PrePersist
    protected void onCreate() {
        updateDate = new Date();
        createDate = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        if(deleted){
            deleteDate = new Date();
        }else{
            updateDate = new Date();
        }
    }
}