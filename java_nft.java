 @Test
    void testNftMetadataUpload() {
        String nftId = "184734734873";
        var metadata = NftMetadata.builder()
                .name("JAVA #" + nftId)
                .image("https://wwww.google.com")
                .description("Java: A popular language")
                .properties(NftMetadata.NftProperties.builder()
                        .bizId("111111")
                        .authorId("32131321")
                        .mintedBySocrates("true")
                        .fileType("video")
                        .source("1")
                        .subCategory("1")
                        .totalSupply("1")
                        .media(List.of(
                                NftMetadata.NftMedia.builder().type(MediaTypeEnum.Video.getValue())
                                        .imageUrl("https://www.aa.com")
                                        .fileUrl("https://www.aa.com")
                                        .build(),
                                NftMetadata.NftMedia.builder().type(MediaTypeEnum.Audio.getValue())
                                        .imageUrl("https://www.aa.com")
                                        .fileUrl("https://www.aa.com")
                                        .build(),
                                NftMetadata.NftMedia.builder().type(MediaTypeEnum.Image.getValue())
                                        .imageUrl("https://www.aa.com")
                                        .fileUrl("https://www.aa.com")
                                        .build(),
                                NftMetadata.NftMedia.builder().type(MediaTypeEnum.File.getValue())
                                        .imageUrl("https://www.aa.com")
                                        .fileUrl("https://www.aa.com")
                                        .build()
                        ))
                        .build())
                .build();
        var result = nftMetadataService.upload(nftId, metadata);
        log.info("s3 metadata url is: {}", result);

    }
