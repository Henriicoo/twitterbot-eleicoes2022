package com.henriquenapimo1;

import io.quickchart.QuickChart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageGenerator {

    private static Candidato candidato;

    private static String createCandidatoChart() {
        QuickChart chart = new QuickChart();

        chart.setWidth(191);
        chart.setHeight(191);
        chart.setVersion("2");

        chart.setConfig("{" +
                "  type: 'radialGauge'," +
                "  data: {" +
                "    datasets: [{" +
                "      data: ["+candidato.porcent+"]," +
                "      backgroundColor: getGradientFillHelper('horizontal',['blue','"+candidato.cor()+"'])," +
                "    }]" +
                "  }," +
                "  options: {" +
                "    domain: [0, 100]," +
                "    trackColor: '#cccccc'," +
                "    centerPercentage: 90," +
                "    centerArea: {" +
                "      displayText: false," +
                "    }," +
                "  }" +
                "}");

        return chart.getUrl();
    }

    private static String createProgress(double progress) {
        QuickChart chart = new QuickChart();

        chart.setWidth(420);
        chart.setHeight(5);
        chart.setVersion("2");

        chart.setConfig("{" +
                "            type: 'progressBar'," +
                "                    data: {" +
                "            datasets: [{" +
                "                data: ["+progress+"]," +
                "                backgroundColor: getGradientFillHelper('horizontal', ['#04ADBF', '#F2B807'])," +
                "            }]" +
                "        }," +
                "            options: {" +
                "                plugins: {" +
                "                    datalabels: {" +
                "                        display: false," +
                "                    }" +
                "                }" +
                "            }," +
                "        }");

        return chart.getUrl();
    }

    public static void createCandidatoImage(Candidato cand) throws IOException {
        candidato = cand;

        URL graficoURL = new URL(createCandidatoChart());
        BufferedImage grafico = ImageIO.read(graficoURL);

        BufferedImage imagem = new BufferedImage(250,375,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();

        BufferedImage avatar = ImageIO.read(new File("src/main/resources/"+cand.nome.toLowerCase()+".png"));
        graphics.drawImage(avatar,39,21,172,172,null);

        BufferedImage borda = ImageIO.read(new File("src/main/resources/borda.png"));
        graphics.drawImage(borda,0,0,250,375,null);

        graphics.drawImage(grafico,30,11,191,191,null);

        Font font = new Font("Open Sans Bold", Font.BOLD, 26);

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
        graphics.setColor(Color.decode("#0901f7"));
        graphics.setFont(font);

        metrics = graphics.getFontMetrics(font);
        x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 246+metrics.getHeight());

        // VOTOS
        text = cand.votos+" votos";
        font = new Font("Open Sans Regular",Font.BOLD,16);
        graphics.setFont(font);

        metrics = graphics.getFontMetrics(font);
        x = (imagem.getWidth()-metrics.stringWidth(text))/2;

        graphics.drawString(text, x, 290+metrics.getHeight());

        File file  = new File("src/main/resources/imagem-"+cand.pos+".png");

        ImageIO.write(imagem,"png",file);
    }

    public static void createFinalImage(double prog) throws IOException {
        URL progressURL = new URL(createProgress(prog));
        BufferedImage progress = ImageIO.read(progressURL);

        BufferedImage imagem = new BufferedImage(500,375,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();

        BufferedImage candidato = ImageIO.read(new File("src/main/resources/imagem-1.png"));
        graphics.drawImage(candidato,0,0,null);

        candidato = ImageIO.read(new File("src/main/resources/imagem-2.png"));
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
        graphics.drawString(text,406,336+metrics.getHeight());


        ImageIO.write(imagem,"png",new File("src/main/resources/"+Utils.finalImageFile));
    }
}
