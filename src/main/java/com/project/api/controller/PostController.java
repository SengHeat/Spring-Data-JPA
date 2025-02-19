package com.project.api.controller;


import com.project.api.model.base.ApiResponse;
import com.project.api.model.base.PaginatedResponse;
import com.project.api.model.entity.PostEntity;
import com.project.api.model.request.PostRequest;
import com.project.api.model.response.PostResponse;
import com.project.api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PostRequest request) throws Exception {
        PostEntity post = this.postService.create(request);
        PostResponse response = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
        return ResponseEntity.ok(new ApiResponse("Post created successfully!", 200, response));
    }

/*    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestParam("title") String title,        // Form field "title"
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "file", required = false) MultipartFile file     // File field "file"
    ) throws Exception {
        // Create a new PostRequest object to pass to your service
        PostRequest request = new PostRequest();
        request.setTitle(title);
        request.setContent(content);

        // Optionally, you could process the file here if needed
        String fileName = file.getOriginalFilename();
        // Save the file, process it, or store it in a specific location

        System.out.println("File Upload name:"+fileName);

        // Call the service to create the post
        PostEntity post = this.postService.create(request);

        // Create a response object
        PostResponse response = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );

        return ResponseEntity.ok(new ApiResponse("Post created successfully!", 200, response));
    }*/

  /*  @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(
            @RequestParam("title") String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws Exception {

        // Directory to store uploaded files
        String uploadDir = System.getProperty("user.dir") + "/assets/";

        // Ensure the uploads directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();  // Create the directory if it doesn't exist
        }

        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();

            // Generate a unique file name
            String uniqueFileName = UUID.randomUUID().toString() + "-" + originalFileName;

            // Save the file to the uploads directory
            try {
                Files.copy(file.getInputStream(), Paths.get(uploadDir + uniqueFileName));
                System.out.println("File uploaded successfully: " + uniqueFileName);
            } catch (IOException e) {
                System.out.println("Error while uploading file: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("Error while uploading file.", 500, null));
            }
        }

        // Continue with post creation
        PostRequest request = new PostRequest();
        request.setTitle(title);
        request.setContent(content);

        PostEntity post = this.postService.create(request);

        PostResponse response = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );

        return ResponseEntity.ok(new ApiResponse("Post created successfully!", 200, response));
    }*/


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable("id") String idStr) {
        PostEntity post = this.postService.findOne(idStr);
        return ResponseEntity.ok(new ApiResponse("Post retrieved successfully", 200, PostResponse.fromEntity(post)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("asc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        PaginatedResponse<PostResponse> paginatedResponse = this.postService.findAll(pageable);

        return ResponseEntity.ok(new ApiResponse("", 200, paginatedResponse));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody PostRequest request, @PathVariable("id") String idStr) {
        PostEntity post = this.postService.update(request, idStr);
        PostResponse response = PostResponse.fromEntity(post);

        return ResponseEntity.ok(new ApiResponse("Update successfully", 200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") String idStr) {
        PostEntity post = this.postService.delete(idStr);
        PostResponse response = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
        return ResponseEntity.ok(new ApiResponse("delete", 200, response));
    }

}
