package com.example.baygo.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${aws_access_key_id}")
    private String AWS_ACCESS_KEY;
    @Value("${aws_secret_access_key}")
    private String AWS_SECRET_KEY;
    @Value("${aws_s3_region}")
    private String REGION;

    @Bean
    S3Client s3Client() {

        Region regions = Region.of(REGION);

        final AwsBasicCredentials credentials = AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        return S3Client.builder()
                .region(regions)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}


