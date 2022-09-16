package maven.demo;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import spark.Request;
import spark.Response;


public class MusicService {

	private MusicDAO musicDAO = new MusicDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_FAIXA = 1;
	private final int FORM_ORDERBY_ARTISTAS = 2;
	private final int FORM_ORDERBY_VIEWS = 3;
	
	
	public MusicService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Music(), FORM_ORDERBY_ARTISTAS);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Music(), orderBy);
	}

	
	public void makeForm(int tipo, Music music, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umMusic = "";
		if(tipo != FORM_INSERT) {
			umMusic += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/Music/list/1\">Nova Musica</a></b></font></td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t</table>";
			umMusic += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/Music/";
			String faixa, album, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				faixa = "Inserir Faixa";
				album = "Inserir Album";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + music.getFaixa();
				faixa = "Atualizar Musica (Faixa " + music.getFaixa() + ")";
				album = music.getAlbum();
				buttonLabel = "Atualizar";
			}
			umMusic += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umMusic += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + faixa + "</b></font></td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" faixa=\"album\" value=\""+ music.getAlbum() +"\"></td>";
			umMusic += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" faixa=\"preco\" value=\""+ music.getViews() +"\"></td>";
			umMusic += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" faixa=\"quantidade\" value=\""+ music.getGenero() +"\"></td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" faixa=\"dataFabricacao\" value=\""+ music.getAlbum() + "\"></td>";
			umMusic += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t</table>";
			umMusic += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umMusic += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Musica (Faixa " + music.getFaixa() + ")</b></font></td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td>&nbsp;Descrição: "+ music.getAlbum() +"</td>";
			umMusic += "\t\t\t<td>Preco: "+ music.getViews() +"</td>";
			umMusic += "\t\t\t<td>Quantidade: "+ music.getGenero() +"</td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t\t<tr>";
			umMusic += "\t\t\t<td>&nbsp;Data de fabricação: "+ music.getArtistas() + "</td>";
			umMusic += "\t\t\t<td>&nbsp;</td>";
			umMusic += "\t\t</tr>";
			umMusic += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-MUSIC>", umMusic);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Music</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/music/list/" + FORM_ORDERBY_FAIXA + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/music/list/" + FORM_ORDERBY_ARTISTAS + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/music/list/" + FORM_ORDERBY_VIEWS + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Music> musics;
		if (orderBy == FORM_ORDERBY_FAIXA) {                 	musics = musicDAO.getOrderByFaixa();
		} else if (orderBy == FORM_ORDERBY_ARTISTAS) {		musics = musicDAO.getOrderByArtistas();
		} else if (orderBy == FORM_ORDERBY_VIEWS) {			musics = musicDAO.getOrderByViews();
		} else {											musics = musicDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Music p : musics) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getFaixa() + "</td>\n" +
            		  "\t<td>" + p.getAlbum() + "</td>\n" +
            		  "\t<td>" + p.getViews() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/music/" + p.getFaixa() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/music/update/" + p.getFaixa() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletemusic('" + p.getFaixa() + "', '" + p.getAlbum() + "', '" + p.getViews() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-music>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String Faixa = request.queryParams("Faixa");
		String Album = request.queryParams("Album");
		String Artista = request.queryParams("Artista");
		int Views = Integer.parseInt(request.queryParams("Views"));
		String Genero = request.queryParams("Genero");		
		String resp = "";
		
		Music music = new Music(Faixa, Album, Artista, Views, Genero);
		
		if(musicDAO.insert(music) == true) {
            resp = "music (" + Album + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "music (" + Album + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		String Faixa = request.queryParams(":Faixa");
		String Album = request.queryParams(":Album");		
		Music music = (Music) musicDAO.get(Faixa, Album);
		
		if (music != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, music, FORM_ORDERBY_ARTISTAS);
        } else {
            response.status(404); // 404 Not found
            String resp = "music " + Faixa + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String Faixa = request.queryParams(":Faixa");
		String Album = request.queryParams(":Album");		
		Music music = (Music) musicDAO.get(Faixa, Album);
		
		if (music != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, music, FORM_ORDERBY_ARTISTAS);
        } else {
            response.status(404); // 404 Not found
            String resp = "Musica " + Faixa + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
		String Faixa = request.queryParams(":Faixa");
		String Album = request.queryParams(":Album");		
		Music music = (Music) musicDAO.get(Faixa, Album);
        String resp = "";       

        if (music != null) {
        	music.setAlbum(request.queryParams("Album"));
        	music.setArtistas(request.queryParams("Artistas"));
        	music.setViews(Integer.parseInt(request.queryParams("Views")));
        	music.setGenero(request.queryParams("Genero"));
        	musicDAO.update(music);
        	response.status(200); // success
            resp = "Music (Faixa " + music.getFaixa() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Music (Faixa \" + music.getFaixa() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
		String Faixa = request.queryParams(":Faixa");
		String Album = request.queryParams(":Album");		
		Music music = (Music) musicDAO.get(Faixa, Album);
        String resp = "";       

        if (music != null) {
            musicDAO.delete(Faixa);
            response.status(200); // success
            resp = "Musica (" + Faixa + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Musica (" + Faixa + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" faixa=\"msg\" value=\""+ resp +"\">");
	}
}
