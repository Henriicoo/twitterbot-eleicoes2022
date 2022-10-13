package com.henriquenapimo1.imagens;

import com.henriquenapimo1.obj.Candidato;
import com.henriquenapimo1.obj.Estado;
import com.henriquenapimo1.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ImageGenerator {

    public static void createCandidatoImage(Candidato cand) throws IOException {

        Color cor = Color.BLUE;
        if(cand.cor().equals("red"))
            cor = Color.RED;

        URL graficoURL = new URL(ChartGenerator.createCandidatoChart(cand));
        BufferedImage grafico = ImageIO.read(graficoURL);

        BufferedImage imagem = new BufferedImage(250,375,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();

        BufferedImage avatar = ImageIO.read(new File("src/main/resources/"+cand.nome.toLowerCase()+".png"));
        graphics.drawImage(avatar,39,21,172,172,null);

        BufferedImage borda = ImageIO.read(new File("src/main/resources/borda.png"));
        graphics.drawImage(borda,0,0,250,375,null);

        graphics.drawImage(grafico,30,11,191,191,null);

        Font font = new Font("Open Sans Bold", Font.BOLD, 26);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // NOME
        String text = cand.nome;
        graphics.setColor(Color.decode("#000000"));
        graphics.setFont(font);

        FontMetrics metrics = graphics.getFontMetrics(font);
        int x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 194+metrics.getHeight());

        // PARTIDO E NÚMERO
        text = cand.partido+" - "+cand.num;
        font = new Font("Open Sans Condensed Regular",Font.BOLD,12);
        graphics.setFont(font);

        metrics = graphics.getFontMetrics(font);
        x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 228+metrics.getHeight());

        // PORCENTAGEM
        text = cand.porcent+"%";
        font = new Font("Open Sans Bold",Font.BOLD,38);
        graphics.setColor(cor);
        graphics.setFont(font);

        metrics = graphics.getFontMetrics(font);
        x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 246+metrics.getHeight());

        // VOTOS
        text = cand.votos+" votos";
        font = new Font("Open Sans Regular",Font.PLAIN,16);
        graphics.setFont(font);

        metrics = graphics.getFontMetrics(font);
        x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 290+metrics.getHeight());

        File file  = new File("src/main/resources/gen/imagem-"+cand.pos+".png");

        ImageIO.write(imagem,"png",file);
    }

    public static void createFinalImage(double prog) throws IOException {
        URL progressURL = new URL(ChartGenerator.createProgress(prog));
        BufferedImage progress = ImageIO.read(progressURL);

        BufferedImage imagem = new BufferedImage(500,375,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        BufferedImage candidato = ImageIO.read(new File("src/main/resources/gen/imagem-1.png"));
        graphics.drawImage(candidato,0,0,null);

        candidato = ImageIO.read(new File("src/main/resources/gen/imagem-2.png"));
        graphics.drawImage(candidato,250,0,null);

        graphics.drawImage(progress,(imagem.getWidth() - progress.getWidth())/2,330,null);

        Font font = new Font("Open Sans SemiBold", Font.BOLD, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        graphics.setFont(font);
        graphics.setColor(Color.decode("#000000"));

        // PROGRESSO
        String text = prog+"% DAS SEÇÕES APURADAS";
        graphics.drawString(text,40,336+metrics.getHeight());

        // FONTE
        text = "FONTE: TSE";
        graphics.drawString(text,400,336+metrics.getHeight());


        ImageIO.write(imagem,"png",new File("src/main/resources/gen/"+ Utils.finalImageFile));
    }

    public static void createEstadosChart(List<Estado> estados) throws IOException {
        String graf1 = ChartGenerator.createEstChart(estados.subList(0,9));
        String graf2 = ChartGenerator.createEstChart(estados.subList(9,18));
        String graf3 = ChartGenerator.createEstChart(estados.subList(18,27));

        BufferedImage imagem = ImageIO.read(new URL(graf1));
        ImageIO.write(imagem,"png",new File("src/main/resources/gen/grafico1.png"));

        imagem = ImageIO.read(new URL(graf2));
        ImageIO.write(imagem,"png",new File("src/main/resources/gen/grafico2.png"));

        imagem = ImageIO.read(new URL(graf3));
        ImageIO.write(imagem,"png",new File("src/main/resources/gen/grafico3.png"));
    }

    public static void createMapa(List<Estado> estados) throws IOException {
        BufferedImage imagem = new BufferedImage(700,373,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = imagem.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,imagem.getWidth(),imagem.getHeight());

        estados.forEach(e -> {
            try {
                BufferedImage estado = changeColor(e.nm,Double.parseDouble(e.votosL.replace(",",".")) > Double.parseDouble(e.votosB.replace(",",".")));
                graphics.drawImage(estado,163,0,374,374,null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageIO.write(imagem,"png",new File("src/main/resources/gen/mapa.png"));
    }

    private static BufferedImage changeColor(String estado, boolean red) throws IOException {
        BufferedImage image = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
        image.createGraphics().drawImage(ImageIO.read(new File("src/main/resources/estados/"+estado+".png")),0,0,null);

        int width = image.getWidth();
        int height = image.getHeight();

        Color cor = Color.BLUE;
        if(red)
            cor = Color.RED;

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(image.getRGB(xx, yy),true);
                if (originalColor.getAlpha() >= 100) {
                    image.setRGB(xx, yy, cor.getRGB());
                }
            }
        }
        return image;
    }
}
