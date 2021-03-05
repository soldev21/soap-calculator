package com.rest.soap.wrapper.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "log_child")
@Data
public class ChildLogEnt {
    @Id
    @GeneratedValue
    private int id;
    private int parentId;
    private Date insertDate;
    private String value;
}
