package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "contents")
    private byte[] contents;

    @Column(name = "contents_content_type")
    private String contentsContentType;

    @Column(name = "read_cnt")
    private Integer readCnt;

    @Column(name = "good_cnt")
    private Integer goodCnt;

    @Column(name = "bad_cnt")
    private Integer badCnt;

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
    @JsonIgnoreProperties(value = { "posts" }, allowSetters = true)
    private Board board;

    @JsonIgnoreProperties(value = { "post", "attaches" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
    private AttachGroup attachGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public Post status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContents() {
        return this.contents;
    }

    public Post contents(byte[] contents) {
        this.setContents(contents);
        return this;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getContentsContentType() {
        return this.contentsContentType;
    }

    public Post contentsContentType(String contentsContentType) {
        this.contentsContentType = contentsContentType;
        return this;
    }

    public void setContentsContentType(String contentsContentType) {
        this.contentsContentType = contentsContentType;
    }

    public Integer getReadCnt() {
        return this.readCnt;
    }

    public Post readCnt(Integer readCnt) {
        this.setReadCnt(readCnt);
        return this;
    }

    public void setReadCnt(Integer readCnt) {
        this.readCnt = readCnt;
    }

    public Integer getGoodCnt() {
        return this.goodCnt;
    }

    public Post goodCnt(Integer goodCnt) {
        this.setGoodCnt(goodCnt);
        return this;
    }

    public void setGoodCnt(Integer goodCnt) {
        this.goodCnt = goodCnt;
    }

    public Integer getBadCnt() {
        return this.badCnt;
    }

    public Post badCnt(Integer badCnt) {
        this.setBadCnt(badCnt);
        return this;
    }

    public void setBadCnt(Integer badCnt) {
        this.badCnt = badCnt;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Post createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Post createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public Post modifiedAt(ZonedDateTime modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Post modifiedBy(Long modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Post board(Board board) {
        this.setBoard(board);
        return this;
    }

    public AttachGroup getAttachGroup() {
        return this.attachGroup;
    }

    public void setAttachGroup(AttachGroup attachGroup) {
        if (this.attachGroup != null) {
            this.attachGroup.setPost(null);
        }
        if (attachGroup != null) {
            attachGroup.setPost(this);
        }
        this.attachGroup = attachGroup;
    }

    public Post attachGroup(AttachGroup attachGroup) {
        this.setAttachGroup(attachGroup);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return getId() != null && getId().equals(((Post) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", title='" + getTitle() + "'" +
            ", contents='" + getContents() + "'" +
            ", contentsContentType='" + getContentsContentType() + "'" +
            ", readCnt=" + getReadCnt() +
            ", goodCnt=" + getGoodCnt() +
            ", badCnt=" + getBadCnt() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            "}";
    }
}
