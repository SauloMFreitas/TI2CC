package maven.demo;

public class Music {

	private String Faixa;
	private String Album;
	private String Artistas;
	private String Genero;
	private int Views;
	
	public Music() {
		this.Faixa = "";
		this.Album = "";
		this.Artistas = "";
		this.Genero = "";
		this.Views = 0;
	}
	
	public Music(String Faixa, String Album, String Artistas, int Views, String Genero) {
		this.Faixa = Faixa;
		this.Album = Album;
		this.Artistas = Artistas;
		this.Genero = Genero;
		this.Views = Views;
	}

	public String getFaixa() {
		return Faixa;
	}

	public void setFaixa(String Faixa) {
		this.Faixa = Faixa;
	}

	public String getAlbum() {
		return Album;
	}

	public void setAlbum(String Album) {
		this.Album = Album;
	}

	public String getArtistas() {
		return Artistas;
	}

	public void setArtistas(String Artistas) {
		this.Artistas = Artistas;
	}

	public String getGenero() {
		return Genero;
	}

	public void setGenero(String Genero) {
		this.Genero = Genero;
	}
	
	public int getViews() {
		return Views;
	}

	public void setViews(int Views) {
		this.Views = Views;
	}

	@Override
	public String toString() {
		return "Usuario [Faixa=" + Faixa + ", Album=" + Album + ", Artistas=" + Artistas + ", Genero=" + Genero +", Views=" + Views + "]";
	}	
}
