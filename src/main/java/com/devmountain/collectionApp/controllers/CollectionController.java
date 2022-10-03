package com.devmountain.collectionApp.controllers;


import com.devmountain.collectionApp.dtos.CollectionDto;
import com.devmountain.collectionApp.dtos.ItemDto;
import com.devmountain.collectionApp.dtos.UserDto;
import com.devmountain.collectionApp.repositories.CollectionRepository;
import com.devmountain.collectionApp.util.FileUploadUtil;
import com.devmountain.collectionApp.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.devmountain.collectionApp.services.CollectionService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/user/{userId}")
    public List<CollectionDto> getCollectionsByUser(@PathVariable Long userId){
        return collectionService.getAllCollectionsByUserId(userId);
    }

//        add a new collection
    @PostMapping("/user/{userId}")
    public void addCollection(@RequestBody CollectionDto collectionDto, @PathVariable Long userId){
        collectionService.addCollection(collectionDto, userId);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollectionById(@PathVariable Long collectionId){
        collectionService.deleteCollectionById(collectionId);
    }

    @PutMapping
    public void updateCollection(@RequestBody CollectionDto collectionDto){
        collectionService.updateCollectionById(collectionDto);
    }

    @GetMapping("/{collectionId}")
    public Optional<CollectionDto> getCollectionById(@PathVariable Long collectionId){
        return collectionService.getCollectionById(collectionId);
    }

//    @PostMapping("/upload/image")
//    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image") MultipartFile file)
//            throws IOException {
//
//        collectionService.save(CollectionDto.builder()
//                .name(file.getOriginalFilename())
//                .image(ImageUtility.compressImage(file.getBytes())).build());
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ImageUploadResponse("Image uploaded successfully: " +
//                        file.getOriginalFilename()));
//    }

//    @GetMapping(path = {"/image/{collectionId}"})
//    public ResponseEntity<byte[]> getImageById(@PathVariable("collectionId") Long collectionId) throws IOException {
//
//        final Optional<CollectionDto> collectionOptional = collectionService.getCollectionById(collectionId);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.valueOf(collectionOptional.get().getName()))
//                .body(ImageUtility.decompressImage(collectionOptional.get().getImage()));
//    }

//    @PostMapping("/user/{userId}")
//    public void addCollection(@RequestBody CollectionDto collectionDto, @PathVariable Long userId){
//        collectionService.addCollection(collectionDto, userId);
//    }

//    @PostMapping("/user/{userId}")
//    public void addCollection(@RequestBody CollectionDto collectionDto,
//                                 @PathVariable Long userId,
//                                 @RequestParam("file") MultipartFile file) {
//        collectionService.addCollection(userId, file, collectionDto);
//    }





//    @PostMapping("/save")
//    public String savePhoto(@ModelAttribute(name = "photo") CollectionDto collectionDto,
//    RedirectAttributes ra,  @PathVariable Long userId,
//    @RequestParam("image") MultipartFile multipartFile) throws IOException{
//    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//    collectionDto.setPhoto(fileName);
//    CollectionDto savedCollection = collectionService.addCollection(collectionDto, userId);
//
//    String uploadDir = "./collection-photos/" + savedCollection.getId();
//
//    Path uploadPath = Paths.get(uploadDir);
//
//    if (!Files.exists(uploadPath)){
//        Files.createDirectories(uploadPath);
//    }
//
//    try (InputStream inputStream = multipartFile.getInputStream())
//        {
//            Path filePath = uploadPath.resolve(fileName);
//            System.out.println(filePath.toString());
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IOException("Could not save uploaded file:" + fileName);
//        }
//
//        ra.addFlashAttribute("message", "The collection has been added successfully.");
//        return "redirect:/collections";
//    }

//    @Autowired
//    private UserRepository repo;

//    @PostMapping("/save")
//    public RedirectView saveCollection(CollectionDto collectionDto,
//                                       @RequestParam("image") MultipartFile multipartFile) throws IOException {
//
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        collectionDto.setPhoto(fileName);
//
//        CollectionDto savedCollectionDto = (CollectionDto) CollectionRepository.save(collectionDto);
//
//        String uploadDir = "collection-photos/" + savedCollectionDto.getId();
//
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//
//        return new RedirectView("/collections", true);
//    }


}
