package com.example.baygo.api.s3;

import com.example.baygo.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
@CrossOrigin(origins = "*")
@Tag(name = "S3 API", description = "API for working with files in Amazon S3 storage")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "Upload a file to S3 bucket", description = "Uploads a file to the specified S3 bucket.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Map<String, String>> uploadFiles(@RequestParam("files") MultipartFile files) throws IOException {
        return ResponseEntity.ok(s3Service.uploadFile(files));
    }

    @DeleteMapping
    @Operation(summary = "Delete a file", description = "This method to delete a file from S3 bucket.")
    public ResponseEntity<Map<String, String>> deleteFile(@RequestParam("fileLink") String fileLink) {
        return ResponseEntity.ok(s3Service.deleteFile(fileLink));
    }
}
