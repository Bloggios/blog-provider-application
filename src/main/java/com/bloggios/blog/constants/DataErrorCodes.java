package com.bloggios.blog.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.constants
 * Created_on - May 22 - 2024
 * Created_at - 16:50
 */

@UtilityClass
public class DataErrorCodes {
    public static final String INVALID_UUID = "DE__BLOG-2001";
    public static final String TITLE_MANDATORY = "DE__BLOG-2002";
    public static final String TITLE_WORD_LIMIT_EXCEED = "DE__BLOG-2003";
    public static final String TITLE_LENGTH_LIMIT_EXCEED = "DE__BLOG-2004";
    public static final String DETAILS_NOT_PRESENT = "DE__BLOG-2005";
    public static final String IMAGES_NULL = "DE__BLOG-2006";
    public static final String IMAGES_LIST_SIZE_NOT_MATCHED_WITH_HTML = "DE__BLOG-2007";
    public static final String BLOG_BODY_LIMIT_EXCEED = "DE__BLOG-2008";
    public static final String TAGS_LIMIT_EXCEED = "DE__BLOG-2009";
    public static final String BLANK_SPACING_IN_TAG = "DE__BLOG-2010";
    public static final String TAGS_NOT_PRESENT = "DE__BLOG-2011";
    public static final String IMAGES_LIMIT_EXCEED = "DE__BLOG-2012";
    public static final String DUPLICATE_IMAGES_NOT_ALLOWED_TO_UPLOAD = "DE__BLOG-2013";
    public static final String NOT_IMAGE_TYPE = "DE__BLOG-2014";
    public static final String IMAGE_SIZE_LIMIT_EXCEED = "DE__BLOG-2015";
    public static final String INVALID_IMAGE_NAME = "DE__BLOG-2016";
    public static final String DRAFT_BLOG_CANNOT_SCHEDULED = "DE__BLOG-2017";
    public static final String BLOG_CANNOT_BE_SCHEDULED_FOR_MORE_THAN_ONE_DAY = "DE__BLOG-2018";
    public static final String COVER_NOT_IMAGE_TYPE = "DE__BLOG-2019";
    public static final String COVER_IMAGE_SIZE_LIMIT_EXCEED = "DE__BLOG-2020";
    public static final String COVER_INVALID_IMAGE_NAME = "DE__BLOG-2021";
    public static final String CHAPTER_ID_NOT_FOUND = "DE__BLOG-2022";
}
