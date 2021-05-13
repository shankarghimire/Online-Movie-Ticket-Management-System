package ca.sheriancollege.ghimirsh.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Movie")
@Table(name="tbl_movie")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="movieId")
	private Long id;
	
	@Column(name="movie_name")
	private String movieName;
	
	@Column(name="movie_genre")
	private String movieGenre;
	
	@Column(name="language")
	private String language;
	
	@Column(name="image_path")
	private String imagePath;
	
	@Column(name="show_time")
	private String showTime;
	public Movie() {
		
	}

	public Movie(Long id, String movieName, String movieGenre, String language, String imagePath, String showTime) {
		super();
		this.id = id;
		this.movieName = movieName;
		this.movieGenre = movieGenre;
		this.language = language;
		this.imagePath = imagePath;
		this.showTime = showTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", movieName=" + movieName + ", movieGenre=" + movieGenre + ", language=" + language
				+ ", imagePath=" + imagePath + ", showTime=" + showTime + "]";
	}

}
