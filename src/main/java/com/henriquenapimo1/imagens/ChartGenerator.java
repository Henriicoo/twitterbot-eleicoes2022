package com.henriquenapimo1.imagens;

import com.henriquenapimo1.obj.Candidato;
import com.henriquenapimo1.obj.Estado;
import io.quickchart.QuickChart;

import java.util.List;

public class ChartGenerator {

    public static String createCandidatoChart(Candidato candidato) {
        QuickChart chart = new QuickChart();

        chart.setWidth(191);
        chart.setHeight(191);
        chart.setVersion("2");

        chart.setConfig("{" +
                "  type: 'radialGauge'," +
                "  data: {" +
                "    datasets: [{" +
                "      data: ["+candidato.porcent+"]," +
                "      backgroundColor: getGradientFillHelper('horizontal',['green','"+candidato.cor()+"'])," +
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

    public static String createProgress(double progress) {
        QuickChart chart = new QuickChart();

        chart.setWidth(420);
        chart.setHeight(5);
        chart.setVersion("2");

        chart.setConfig("{" +
                "            type: 'progressBar'," +
                "                    data: {" +
                "            datasets: [{" +
                "                data: ["+progress+"]," +
                "                backgroundColor: getGradientFillHelper('horizontal', ['#04ADBF', '#0901f7'])," +
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

    public static String createEstChart(List<Estado> est) {
        QuickChart chart = new QuickChart();

        chart.setWidth(700);
        chart.setHeight(373);
        chart.setVersion("2");
        chart.setBackgroundColor("white");

        StringBuilder labels = new StringBuilder();
        StringBuilder lula = new StringBuilder();
        StringBuilder bolso = new StringBuilder();
        StringBuilder urna = new StringBuilder();

        est.forEach(uf -> {
            labels.append("\"").append(uf.nm.toUpperCase()).append("\",");
            lula.append(uf.votosL).append(",");
            bolso.append(uf.votosB).append(",");
            urna.append(uf.urnasApuradas).append(",");
        });

        chart.setConfig("{" +
                "  \"type\": \"horizontalBar\"," +
                "  \"data\": {" +
                "    \"labels\": [" +
                labels +
                "    ]," +
                "    \"datasets\": [" +
                "      {" +
                "        \"label\": \"Lula\"," +
                "        \"backgroundColor\": \"red\"," +
                "        \"borderWidth\": 1," +
                "        \"data\": [" +
                lula +
                "        ]" +
                "      }," +
                "      {" +
                "        \"label\": \"Jair Bolsonaro\"," +
                "        \"backgroundColor\": \"blue\"," +
                "        \"data\": [" +
                bolso +
                "        ]" +
                "      }," +
                "      {" +
                "        \"label\": \"Urnas Apuradas\"," +
                "        \"backgroundColor\": \"#cccccc\"," +
                "        \"borderWidth\": 1," +
                "        \"data\": [" +
                urna +
                "        ]" +
                "      }" +
                "    ]" +
                "  }," +
                "  \"options\": {" +
                "    \"elements\": {" +
                "      \"rectangle\": {" +
                "        \"borderWidth\": 2" +
                "      }" +
                "    }," +
                "    \"responsive\": true," +
                "    \"legend\": {" +
                "      \"position\": \"right\"" +
                "    }," +
                "    \"title\": {" +
                "      \"display\": true," +
                "      \"text\": \"Apurações por Estado\"" +
                "    }" +
                "  }" +
                "}");

        return chart.getUrl();
    }
}
