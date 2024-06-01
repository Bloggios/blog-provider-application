/*
 * Copyright © 2023-2024 Rohit Parihar and Bloggios
 * All rights reserved.
 * This software is the property of Rohit Parihar and is protected by copyright law.
 * The software, including its source code, documentation, and associated files, may not be used, copied, modified, distributed, or sublicensed without the express written consent of Rohit Parihar.
 * For licensing and usage inquiries, please contact Rohit Parihar at rohitparih@gmail.com, or you can also contact support@bloggios.com.
 * This software is provided as-is, and no warranties or guarantees are made regarding its fitness for any particular purpose or compatibility with any specific technology.
 * For license information and terms of use, please refer to the accompanying LICENSE file or visit http://www.apache.org/licenses/LICENSE-2.0.
 * Unauthorized use of this software may result in legal action and liability for damages.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bloggios.blog.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - question-provider-application
 * Package - com.bloggios.question.provider.constants
 * Created_on - 12 March-2024
 * Created_at - 17 : 57
 */

@UtilityClass
public class ResponseErrorMessageConstants {

    public static final String IMAGES_LIST_SIZE_NOT_MATCHED_WITH_HTML = "Images to uploaded must be %s";
    public static final String IMAGES_NULL = "Please upload %s images";
    public static final String BLOG_DETAILS_WORD_LIMIT_EXCEED = "Word limit for question details is 400, but you have added %s words";
    public static final String TAGS_NOT_PRESENT = "%s tag(s) are not exist in Bloggios";
    public static final String BLOG_BODY_LIMIT_EXCEED = "Blog cannot contains more than %s characters";
    public static final String IMAGES_LIMIT_EXCEED = "You can add upto %s images in a single Blog";
}
