package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "comment")
    private String comment;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "post", "parent" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "board", "attachGroup" }, allowSetters = true)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "comments", "post", "parent" }, allowSetters = true)
    private Comment parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public Comment depth(Integer depth) {
        this.setDepth(depth);
        return this;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getComment() {
        return this.comment;
    }

    public Comment comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReadCnt() {
        return this.readCnt;
    }

    public Comment readCnt(Integer readCnt) {
        this.setReadCnt(readCnt);
        return this;
    }

    public void setReadCnt(Integer readCnt) {
        this.readCnt = readCnt;
    }

    public Integer getGoodCnt() {
        return this.goodCnt;
    }

    public Comment goodCnt(Integer goodCnt) {
        this.setGoodCnt(goodCnt);
        return this;
    }

    public void setGoodCnt(Integer goodCnt) {
        this.goodCnt = goodCnt;
    }

    public Integer getBadCnt() {
        return this.badCnt;
    }

    public Comment badCnt(Integer badCnt) {
        this.setBadCnt(badCnt);
        return this;
    }

    public void setBadCnt(Integer badCnt) {
        this.badCnt = badCnt;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Comment createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Comment createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public Comment modifiedAt(ZonedDateTime modifiedAt) {
        this.setModifiedAt(modifiedAt);
        return this;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Comment modifiedBy(Long modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setParent(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setParent(this));
        }
        this.comments = comments;
    }

    public Comment comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Comment addComment(Comment comment) {
        this.comments.add(comment);
        comment.setParent(this);
        return this;
    }

    public Comment removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setParent(null);
        return this;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment post(Post post) {
        this.setPost(post);
        return this;
    }

    public Comment getParent() {
        return this.parent;
    }

    public void setParent(Comment comment) {
        this.parent = comment;
    }

    public Comment parent(Comment comment) {
        this.setParent(comment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return getId() != null && getId().equals(((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", depth=" + getDepth() +
            ", comment='" + getComment() + "'" +
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
