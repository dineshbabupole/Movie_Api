package com.movieflex.movieApi.services;

import com.movieflex.movieApi.dtos.MovieDto;
import com.movieflex.movieApi.entites.Movie;
import com.movieflex.movieApi.exceptions.MovieNotFoundException;
import com.movieflex.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieSerivce{
    private final MovieRepository movieRepository;
  private final FileService fileSerivce;
    public MovieServiceImpl(MovieRepository movieRepository, FileService fileSerivce) {
        this.movieRepository = movieRepository;
        this.fileSerivce = fileSerivce;
    }
    @Value("$project.posters")
    private  String path;
    @Value("${base.Url}")
    private String baseUrl;
    @Override
    public MovieDto AddMovie(MovieDto movieDto, MultipartFile file) throws IOException {
       String uploadedFileName=fileSerivce.uploadFile(path,file);
        movieDto.setPoster(uploadedFileName);
        Movie m=new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        Movie movie=movieRepository.save(m);

        String url=baseUrl+"/file/"+path+uploadedFileName;
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                url
        );
    }

    @Override
    public MovieDto getMovie(Integer movieId) throws FileNotFoundException {
       Movie movie= movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie with this id is not found with id:"+movieId));
        String url=baseUrl+"/file/"+movie.getPoster();
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                url
        );

    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> all=movieRepository.findAll();
        List<MovieDto> movieDtos=new ArrayList<>();
        for(Movie movie:all){
            String url=baseUrl+"/file/"+movie.getPoster();
            movieDtos.add(new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    url
            ));
        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        Movie movie= movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie with this id is not found with id:"+movieId));
        String fileName=movie.getPoster();
        if(file!=null){
            Files.deleteIfExists(Paths.get(path+ File.separator+fileName));
            fileName=fileSerivce.uploadFile(path,file);
        }
        movieDto.setPoster(fileName);
       Movie updated= movieRepository.save(new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        ));
        String url=baseUrl+"/file/"+fileName;
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                url
        );

    }

    @Override
    public String delete(Integer movieId) throws IOException {
        Movie movie= movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie with this id is not found with id:"+movieId));
        Files.deleteIfExists(Paths.get(path+File.separator+movie.getPoster()));
        movieRepository.deleteById(movieId);
        return "Movie is Deleted with the Id:"+movieId;
    }

//    @Override
//    public void fileSource(String filename) throws FileNotFoundException {
//        fileSerivce.getResourceFile(path,filename);
//    }

}
