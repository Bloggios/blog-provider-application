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

package com.bloggios.blog.file;

import com.bloggios.blog.payload.record.UploadImagePayloadRecord;
import com.bloggios.blog.utils.RandomGenerators;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.function.Function;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - question-provider-application
 * Package - com.bloggios.question.provider.file
 * Created_on - 13 March-2024
 * Created_at - 17 : 07
 */

@Component
public class UploadFile implements Function<UploadImagePayloadRecord, String> {

    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    @Override
    @SneakyThrows(value = IOException.class)
    public String apply(UploadImagePayloadRecord uploadImagePayloadRecord) {
        String month = LocalDate.now().getMonth().toString();
        MultipartFile multipartFile = uploadImagePayloadRecord.file();
        String path = uploadImagePayloadRecord.path();
        String originalFilename = multipartFile.getOriginalFilename();
        String randomName = RandomGenerators.generateRandomImageName(
                uploadImagePayloadRecord.userId()
        );
        assert originalFilename != null;
        String imageName = randomName.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String imagePath = path + File.separator + month + File.separator + imageName;
        File file = new File(path + File.separator + month);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            logger.warn("""
                    Path Created for Blog Image : {}
                    Image Name : {}
                    Image Path : {}
                    """, mkdir, imageName, imagePath);
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(imagePath));
        return imageName;
    }

    @SneakyThrows(value = IOException.class)
    public String uploadImage(String path, MultipartFile multipartFile, String userId) {
        String month = LocalDate.now().getMonth().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        String randomName = RandomGenerators.generateRandomImageName(userId);
        assert originalFilename != null;
        String imageName = randomName.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String imagePath = path + File.separator + month + File.separator + imageName;
        File file = new File(path + File.separator + month);
        if (!file.exists()) {
            boolean mkdir = file.mkdirs();
            logger.warn("""
                    Path Created : {}
                    Image Name : {}
                    Image Path : {}
                    """, mkdir, imageName, imagePath);
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(imagePath));
        return imageName;
    }
}
