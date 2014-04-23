package com.ugmt.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Puneet
 * 
 *         All common fields of records come here
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = 4643201185226691972L;

    @Id
    @Column(name = "id", nullable = false, length = 40, updatable = false)
    private String id;

    @Column(name = "createdTS", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modifiedTS", nullable = false)
    private Timestamp modifiedAt;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the createdAt
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the modifiedAt
     */
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    /**
     * @param modifiedAt
     *            the modifiedAt to set
     */
    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
