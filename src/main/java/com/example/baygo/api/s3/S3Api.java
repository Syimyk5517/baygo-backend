package com.example.baygo.api.s3;

import com.example.baygo.service.S3Service;
import com.google.api.client.util.Value;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Response;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
@Tag(name = "S3 API", description = "API for working with files in Amazon S3 storage")
public class S3Api {
    @Value("${aws_bucket_name}")
    private String BUCKET_NAME;
    private final S3Service s3Service;

    @Operation(summary = "Upload a file to S3 bucket",
            description = "Uploads a file to the specified S3 bucket.")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<List<Map<String, String>>> uploadFiles(
            @RequestParam("bucketName") String bucketName,
            @RequestParam("files") List<MultipartFile> files
    ){
        return ResponseEntity.ok(s3Service.uploadFile(bucketName, files));

}


    @Operation(summary = "Delete a file from S3 bucket",
            description = "Deletes a file from the specified S3 bucket.")
    @DeleteMapping
    public S3Response deleteFile(@RequestParam("fileLink") String fileLink) {
        return (S3Response) s3Service.deleteFile(fileLink);
    }
}
