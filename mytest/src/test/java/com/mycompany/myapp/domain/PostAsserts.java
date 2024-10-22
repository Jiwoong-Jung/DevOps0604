package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class PostAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostAllPropertiesEquals(Post expected, Post actual) {
        assertPostAutoGeneratedPropertiesEquals(expected, actual);
        assertPostAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostAllUpdatablePropertiesEquals(Post expected, Post actual) {
        assertPostUpdatableFieldsEquals(expected, actual);
        assertPostUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostAutoGeneratedPropertiesEquals(Post expected, Post actual) {
        assertThat(expected)
            .as("Verify Post auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostUpdatableFieldsEquals(Post expected, Post actual) {
        assertThat(expected)
            .as("Verify Post relevant properties")
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getTitle()).as("check title").isEqualTo(actual.getTitle()))
            .satisfies(e -> assertThat(e.getContents()).as("check contents").isEqualTo(actual.getContents()))
            .satisfies(
                e -> assertThat(e.getContentsContentType()).as("check contents contenty type").isEqualTo(actual.getContentsContentType())
            )
            .satisfies(e -> assertThat(e.getReadCnt()).as("check readCnt").isEqualTo(actual.getReadCnt()))
            .satisfies(e -> assertThat(e.getGoodCnt()).as("check goodCnt").isEqualTo(actual.getGoodCnt()))
            .satisfies(e -> assertThat(e.getBadCnt()).as("check badCnt").isEqualTo(actual.getBadCnt()))
            .satisfies(
                e ->
                    assertThat(e.getCreatedAt())
                        .as("check createdAt")
                        .usingComparator(zonedDataTimeSameInstant)
                        .isEqualTo(actual.getCreatedAt())
            )
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()))
            .satisfies(
                e ->
                    assertThat(e.getModifiedAt())
                        .as("check modifiedAt")
                        .usingComparator(zonedDataTimeSameInstant)
                        .isEqualTo(actual.getModifiedAt())
            )
            .satisfies(e -> assertThat(e.getModifiedBy()).as("check modifiedBy").isEqualTo(actual.getModifiedBy()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPostUpdatableRelationshipsEquals(Post expected, Post actual) {
        assertThat(expected)
            .as("Verify Post relationships")
            .satisfies(e -> assertThat(e.getBoard()).as("check board").isEqualTo(actual.getBoard()));
    }
}
