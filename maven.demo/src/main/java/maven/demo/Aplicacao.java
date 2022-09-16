package maven.demo;

import java.util.List;

public class Aplicacao {
	
	public static void main(String[] args) throws Exception {
		
		MusicDAO musicDAO = new MusicDAO();
		
		System.out.println("\n\n==== Inserir usuário === ");
		Music music = new Music("Windows Down", "Elevate", "Big Time Rush",86930630 ,"POP");
		if(musicDAO.insert(music) == true) {
			System.out.println("Inserção com sucesso -> " + music.toString());
		}
		
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Faixa (" + music.getFaixa() + "): " + musicDAO.autenticar("Windows Down", "Elevate"));
			
		System.out.println("\n\n==== Mostrar usuários do sexo masculino === ");
		List<Music> musics = musicDAO.getGeneroPOP();
		for (Music u: musics) {
			System.out.println(u.toString());
		}

		System.out.println("\n\n==== Atualizar senha (código (" + music.getFaixa() + ") === ");
		music.setAlbum(DAO.toMD5("Elevate"));
		musicDAO.update(music);
		
		System.out.println("\n\n==== Testando autenticação ===");
		System.out.println("Usuário (" + music.getFaixa() + "): " +musicDAO.autenticar("Windows Down", DAO.toMD5("Elevate")));		
		
		System.out.println("\n\n==== Invadir usando SQL Injection ===");
		System.out.println("Usuário (" + music.getFaixa() + "): " + musicDAO.autenticar("Windows Down", "x' OR 'x' LIKE 'x"));

		System.out.println("\n\n==== Mostrar usuários ordenados por código === ");
		musics = musicDAO.getOrderByFaixa();
		for (Music u: musics) {
			System.out.println(u.toString());
		}
		
		System.out.println("\n\n==== Excluir Musica (Faixa " + music.getFaixa() + ") === ");
		musicDAO.delete(music.getFaixa());
		
		System.out.println("\n\n==== Mostrar usuários ordenados por views === ");
		musics = musicDAO.getOrderByViews();
		for (Music u: musics) {
			System.out.println(u.toString());
		}
	}
}