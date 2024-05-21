package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Attach} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachDTO implements Serializable {

    private Long id;

    private Integer ord;

    private String name;

    private String origName;

    private String ext;

    private String contentType;

    private String path;

    private Long fileSize;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private Long createdBy;

    private ZonedDateTime modifiedAt;

    private Long modifiedBy;

    private AttachGroupDTO attachGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigName() {
        return origName;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AttachGroupDTO getAttachGroup() {
        return attachGroup;
    }

    public void setAttachGroup(AttachGroupDTO attachGroup) {
        this.attachGroup = attachGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachDTO)) {
            return false;
        }

        AttachDTO attachDTO = (AttachDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachDTO{" +
            "id=" + getId() +
            ", ord=" + getOrd() +
            ", name='" + getName() + "'" +
            ", origName='" + getOrigName() + "'" +
            ", ext='" + getExt() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", path='" + getPath() + "'" +
            ", fileSize=" + getFileSize() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", attachGroup=" + getAttachGroup() +
            "}";
    }
}
