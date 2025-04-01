package com.movieflex.movieApi.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private Integer movieId;

    @NotBlank(message = "enter the proper value in filed!")
    private String title;

    @NotBlank(message = "enter the proper value in filed!")
    private String director;

    @NotBlank(message = "enter the proper value in filed!")
    private String studio;

    private Set<String> movieCast;


    private int releaseYear;

    @NotBlank(message = "enter the proper value in filed!")
    private String poster;

    @NotBlank(message = "enter the proper value in filed!")
    private String posterUrl;
}
