package com.rest.soap.wrapper.entitiy;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "log_parent")
@Data
public class ParentLogEnt {
    @Id
    @GeneratedValue
    private int id;
    private Date insertDate;
}
