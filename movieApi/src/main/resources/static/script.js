const movieForm = document.getElementById("movieForm");
const movieList = document.getElementById("movieList");

const API_URL = "http://localhost:8080/api/movie";  // Adjust this if needed

// Fetch and display movies
async function fetchMovies() {
    try {
        const response = await fetch(`${API_URL}/all`);
        const movies = await response.json();
        movieList.innerHTML = "";

        movies.forEach(movie => {

            const card = document.createElement("div");
            card.classList.add("card");
            card.innerHTML = `
                  <img src="${movie.posterUrl}" alt="${movie.title}">
                      <h3>Title: ${movie.title}</h3>
                      <h3>Director: ${movie.director}</h3>
                      <h3>Studio: ${movie.studio}</h3>
                      <h3>Cast:</h3>
                      <ul>
                          <li>${movie.movieCast[0]}</li>
                          <li>${movie.movieCast[1]}</li>
                          <li>${movie.movieCast[2]}</li> <!-- Corrected index -->
                      </ul>
                      <h3>Release Year: ${movie.releaseYear}</h3>

            `;
            movieList.appendChild(card);
        });
    } catch (error) {
        console.error("Error fetching movies:", error);
    }
}

// Handle adding a movie
movieForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const title = document.getElementById("title").value;
    const poster = document.getElementById("poster").files[0];

    const formData = new FormData();
    formData.append("file", poster);
    formData.append("movieDto", JSON.stringify({ title }));

    try {
        await fetch(`${API_URL}/add-movie`, {
            method: "POST",
            body: formData
        });

        movieForm.reset();
        fetchMovies(); // Refresh movie list
    } catch (error) {
        console.error("Error adding movie:", error);
    }
});

// Initial fetch
fetchMovies();
