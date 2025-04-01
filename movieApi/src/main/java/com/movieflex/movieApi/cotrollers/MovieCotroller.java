package com.movieflex.movieApi.cotrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflex.movieApi.dtos.MovieDto;
import com.movieflex.movieApi.exceptions.EmptyFileException;
import com.movieflex.movieApi.services.MovieSerivce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/movie")
public class MovieCotroller {
    private final MovieSerivce movieSerivce;

    public MovieCotroller(MovieSerivce movieSerivce) {
        this.movieSerivce = movieSerivce;
    }
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestParam String movieDto) throws IOException {
        if(file.isEmpty())throw new EmptyFileException("file is empty with id");
        MovieDto m=stringToJson(movieDto);
        return new ResponseEntity<>(movieSerivce.AddMovie(m,file), HttpStatus.CREATED);
    }
   @GetMapping("/{movieId}")
   public ResponseEntity<MovieDto> getMovie(@PathVariable Integer movieId) throws FileNotFoundException {
        return ResponseEntity.ok(movieSerivce.getMovie(movieId));
   }
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovie()  {
        return ResponseEntity.ok(movieSerivce.getAllMovies());
    }
//    @GetMapping("/poster/{fileName}")
//    public void getPoster(@PathVariable String fileName) throws FileNotFoundException {
//        movieSerivce.fileSource(fileName);
//    }

    @PutMapping("/update/{movieId}")
  public ResponseEntity<MovieDto> updateMovie(@PathVariable Integer movieId,@RequestParam String moviedto,@RequestPart MultipartFile file) throws IOException {
        if(!file.isEmpty())file=null;
        MovieDto m=stringToJson(moviedto);
       return ResponseEntity.ok(movieSerivce.updateMovie(movieId,m,file));
  }
  @DeleteMapping("/delete/{movieId}")
  public ResponseEntity<String> deleteMovie(@PathVariable Integer movieId) throws IOException {
      return  ResponseEntity.ok(movieSerivce.delete(movieId));

  }
    private MovieDto stringToJson(String movieDto) throws JsonProcessingException {
        ObjectMapper ob=new ObjectMapper();
        return ob.readValue(movieDto,MovieDto.class);
    }


}
