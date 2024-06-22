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

package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.constants.EnvironmentConstants;
import com.bloggios.blog.constants.InternalExceptionCodes;
import com.bloggios.blog.constants.ServiceConstants;
import com.bloggios.blog.exception.payloads.BadRequestException;
import com.bloggios.blog.file.UploadFile;
import com.bloggios.blog.modal.embeddable.ImageLinksEntity;
import com.bloggios.blog.payload.record.UploadImagePayloadRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - question-provider-application
 * Package - com.bloggios.question.provider.processor.implementation.function
 * Created_on - 13 March-2024
 * Created_at - 17 : 03
 */

@Component
public class UploadImagesLinkProcessor implements Function<UploadImagePayloadRecord, List<ImageLinksEntity>> {

    private static final Logger logger = LoggerFactory.getLogger(UploadImagesLinkProcessor.class);

    private final UploadFile uploadFile;
    private final Environment environment;

    public UploadImagesLinkProcessor(
            UploadFile uploadFile,
            Environment environment
    ) {
        this.uploadFile = uploadFile;
        this.environment = environment;
    }

    @Override
    public List<ImageLinksEntity> apply(UploadImagePayloadRecord uploadImagePayloadRecord) {
        List<MultipartFile> images = uploadImagePayloadRecord.files();
        List<CompletableFuture<ImageLinksEntity>> futures = images.stream()
                .map(image -> CompletableFuture.supplyAsync(() -> {
                            try {
                                String imageName = uploadFile.apply(
                                        new UploadImagePayloadRecord(
                                                uploadImagePayloadRecord.path(),
                                                uploadImagePayloadRecord.userId(),
                                                image
                                        )
                                );
                                String type = imageName.substring(imageName.lastIndexOf("."));
                                return ImageLinksEntity
                                        .builder()
                                        .size(String.valueOf(image.getSize()))
                                        .type(type)
                                        .name(imageName)
                                        .link(generateLink(imageName, uploadImagePayloadRecord.uploadFor()))
                                        .build();
                            } catch (BadRequestException badRequestException) {
                                throw new BadRequestException(InternalExceptionCodes.ASYNC_IMAGE_UPLOAD_ERROR);
                            }
                        })
                        .exceptionally(ex -> {
                            logger.error("""
                                    Exception Occurred While Uploading Images Asynchronously
                                    Message : {}
                                    Stack Trace : {}
                                    """, ex.getMessage(), ex.getStackTrace());
                            throw new BadRequestException(InternalExceptionCodes.INTERNAL_ERROR);
                        }))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .toList()
        ).join();
    }

    private String generateLink(String imageName, String uploadFor) {
        String profile = environment.getProperty(EnvironmentConstants.APPLICATION_PROFILE);
        assert profile != null;
        String url = environment.getProperty(EnvironmentConstants.ASSETS);
        assert url != null;
        return url +
                "/" +
                uploadFor +
                "/" +
                LocalDate.now().getMonth().toString() +
                "/" +
                imageName;
    }
}
