package com.movieflex.movieApi.services;

import com.movieflex.movieApi.dtos.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MovieSerivce {
    MovieDto AddMovie(MovieDto moviedto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer MovieId) throws FileNotFoundException;
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer MovieId,MovieDto movieDto,MultipartFile file)throws IOException;
    String delete(Integer movieId) throws IOException;
    //void fileSource(String filename) throws FileNotFoundException;
}
