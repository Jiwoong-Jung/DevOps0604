package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attach.
 */
@Entity
@Table(name = "attach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ord")
    private Integer ord;

    @Column(name = "name")
    private String name;

    @Column(name = "orig_name")
    private String origName;

    @Column(name = "ext")
    private String ext;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "path")
    private String path;

    @Column(name = "file_size")
    private Long fileSize;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "post", "attaches" }, allowSetters = true)
    private AttachGroup attachGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attach id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public Attach ord(Integer ord) {
        this.setOrd(ord);
        return this;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getName() {
        return this.name;
    }

    public Attach name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigName() {
        return this.origName;
    }

    public Attach origName(String origName) {
        this.setOrigName(origName);
        return this;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public String getExt() {
        return this.ext;
    }

    public Attach ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Attach contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return this.path;
    }

    public Attach path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Attach fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Attach createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Attach createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public Attach modifiedAt(ZonedDateTime modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Attach modifiedBy(Long modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AttachGroup getAttachGroup() {
        return this.attachGroup;
    }

    public void setAttachGroup(AttachGroup attachGroup) {
        this.attachGroup = attachGroup;
    }

    public Attach attachGroup(AttachGroup attachGroup) {
        this.setAttachGroup(attachGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attach)) {
            return false;
        }
        return getId() != null && getId().equals(((Attach) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attach{" +
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
            "}";
    }
}
